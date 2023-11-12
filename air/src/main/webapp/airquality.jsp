<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Google Map API와 날씨 정보</title>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyANEODhUcpdCG8pStZRxlhU9SuzELykNuk&libraries=places" defer></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
</head>
<body>
    <div style = "float: right">
        <label for="location">위치:</label>
        <input type="text" id="location" placeholder="위치를 입력하세요">
        <button onclick="searchLocation()">검색</button>
        <div><b>조회 : <a href='AirSelect.do'>확인하기</a></b></div>
    </div></br></br></br>
    <div id="map" style="height: 800px; width: 100%;"></div>

    <script>
        var map;
        var infoWindow;
        var marker;

        var openWeatherApiKey = '9621802d90e86da79fb795232db24b6b';
        var airVisualApiKey = '2d59911b-547a-4f59-a735-5cbdd51a20f7';
        var airVisualApiUrl = 'http://api.airvisual.com/v2/nearest_city';

        function initMap() {
            var initialCenter = { lat: 35.1796, lng: 129.0756 };

            map = new google.maps.Map(document.getElementById('map'), {
                center: initialCenter,
                zoom: 12
            });
            infoWindow = new google.maps.InfoWindow();
        }

        document.addEventListener('DOMContentLoaded', function () {
            initMap();
        });

        function searchLocation() {
            var location = document.getElementById('location').value;

            var placesService = new google.maps.places.PlacesService(map);
            placesService.textSearch({ query: location }, function (results, status) {
                if (status === google.maps.places.PlacesServiceStatus.OK) {
                    var place = results[0];
                    showLocation(place);
                } else {
                    alert('위치를 찾을 수 없습니다');
                }
            });
        }

        function showLocation(place) {
            map.setCenter(place.geometry.location);
            map.setZoom(12);

            if (marker) {
                marker.setMap(null);
            }

            marker = new google.maps.Marker({
                map: map,
                position: place.geometry.location,
                title: place.name
            });

            marker.addListener('click', function () {
                fetchWeatherInfo(place.geometry.location.lat(), place.geometry.location.lng());
                
            });
        }
		
        function fetchWeatherInfo(lat, lng) {
			
            var openWeatherMapUrl = 'https://api.openweathermap.org/data/2.5/weather?lat=' + lat + '&lon=' + lng + '&appid=' + openWeatherApiKey + '&units=metric&lang=kr';

            fetch(openWeatherMapUrl)
                .then(response => response.json())
                .then(data => {
                    var airVisual =  airVisualApiUrl + '?lat=' + lat + '&lon=' + lng + '&key=' + airVisualApiKey;
			
                    fetch(airVisual)
                        .then(response => response.json())
                        .then(airVisualData => {
                        	var geocoder = new google.maps.Geocoder();
                            var latlng = new google.maps.LatLng(lat, lng);

                            geocoder.geocode({'latLng': latlng}, function(results, status) {
                                if (status === google.maps.GeocoderStatus.OK) {
                                    if (results[0]) {
                                        var address = results[0].formatted_address;
										var asthma_c;
					
                                        // 인포윈도우에 주소 정보 포함하여 출력
                                        var content = '<div><b>주소 : </b>' + address + '</div>';
                                        content += '<div><b>날짜 : </b>' + moment().format('YYYY-MM-DD') + '</div>';
                                        content += '<div><b>위도 : </b>' + lat.toFixed(4) + '</div>';
                                        content += '<div><b>경도 : </b>' + lng.toFixed(4) + '</div>';
                                        content += '<div><b>온도:</b>' + data.main.temp + '°C</div>';
                                        content += '<div><b>날씨:</b>' + data.weather[0].description + '</div>';
                                        content += '<div><b>미세먼지 농도:</b>' + airVisualData.data.current.pollution.aqius + 'µg/m³</div>';
                                        content += '<div><b>친식 위험도 : </b>';
                                        if(airVisualData.data.current.pollution.aqius >= 165)
                                        {
                                        	content += '천식 위험도는 매우 높습니다.';
                                        	asthma_c = '천식 위험도는 매우 높습니다.';
                                        }
                                        else if(airVisualData.data.current.pollution.aqius >= 49)
                                        {
                                        	content += '천식 위험도는 높습니다.';
                                        	asthma_c = '천식 위험도는 높습니다.';
                                        }
                                        else if(airVisualData.data.current.pollution.aqius >= 21)
                                        {
                                        	content += '천식 위험도는 보통입니다.';
                                        	asthma_c = '천식 위험도는 보통니다.';
                                        }
										else
										{
											content += '천식 위험도는 낮습니다.' + '</div>';
											asthma_c = '천식 위험도는 낮습니다.';
										}
                                        content += "<div><b>저장 : <a href='airInsert.do?ADDRESS=" + address 
                                        	                                         + "&BASC_DT=" + moment().format('YYYY-MM-DD')
                                        	                                         + "&LAT=" + lat.toFixed(4)
                                        	                                         + "&LNG=" + lng.toFixed(4)
                                        	                                         + "&TEMP=" + data.main.temp
                                        	                                         + "&WEATHER=" + data.weather[0].description
                                        	                                         + "&AQIUS=" + airVisualData.data.current.pollution.aqius
                                        	                                         + "&ASTHMA=" + asthma_c
                                        		 + "'>저장하기</a></b></div>";
                                        infoWindow.setContent(content);
                                        infoWindow.open(map, marker);
                                    }
                                } else {
                                    console.error('주소 정보를 가져오는 중 오류 발생:', status);
                                }
                            });
                        })
                        .catch(error => console.error('대기질 정보를 가져오는 중 오류 발생:', error));
                })
                .catch(error => console.error('날씨 정보를 가져오는 중 오류 발생:', error));
        }
    </script>
</body>
</html>
