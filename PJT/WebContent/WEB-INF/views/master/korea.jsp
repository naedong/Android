<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="euc-kr"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">

    <head>
  
    <script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    	<script
    	 src="//d3js.org/d3.v3.min.js"></script>
        <meta charset="euc-kr" />
        <title>koreaMap</title>
        <link 
        rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/korea.css" />
    </head>
      <script 
      src="${pageContext.request.contextPath}/resources/js/index.js"></script>
    <script
     type="text/javascript" src="${pageContext.request.contextPath}/resources/js/d3.js"></script>
    <script 
    type="text/javascript">
    window.onload = function() {
        drawMap('#container');
    };

    function drawMap(target) {
        var width = 700; 
        var height = 700; 
        var initialScale = 5500; 
        var initialX = -11900; 
        var initialY = 4050; 
        var labels;

        var projection = d3.geo
            .mercator()
            .scale(initialScale)
            .translate([initialX, initialY]);
        var path = d3.geo.path().projection(projection);
        var zoom = d3.behavior
            .zoom()
            .translate(projection.translate())
            .scale(projection.scale())
            .scaleExtent([height, 800 * height])
            .on('zoom', zoom);

        var svg = d3
            .select(target)
            .append('svg')
            .attr('width', width + 'px')
            .attr('height', height + 'px')
            .attr('id', 'map')
            .attr('class', 'map');

        var states = svg
            .append('g')
            .attr('id', 'states')
            .call(zoom);

        var body = d3.select("body");
        var h1 = body.append("h1");
        h1.text("h1 테스트");
        	
        
        states
            .append('rect')
            .attr('class', 'background')
            .attr('width', width + 'px')
            .attr('height', height + 'px');

        d3.json('${pageContext.request.contextPath}/resources/json/korea.json', function(json) {
            states
                .selectAll('path') //지역 설정
                .data(json.features)
                .enter()
                .append('path')
                .attr('d', path)
                .attr('id', function(d) {
                    return 'path-' + d.properties.name_eng;
                });
            labels = states
                .selectAll('text')
                .data(json.features) //라벨표시
                .enter()
                .append('text')
                .attr('transform', translateTolabel)
                .attr('id', function(d) {
                    return 'label-' + d.properties.name_eng;
                })
                .attr('text-anchor', 'middle')
                .attr('dy', '.35em')
                .on('click', d => {
                	console.log(d)
                	location="mastercShop?name="+d.properties.name_eng;
                })
                .text(function(d) {
                    return d.properties.name_eng;
                });
            
            
        });
  		
        
      

        function translateTolabel(d) {
            var arr = path.centroid(d);
            if (d.properties.code == 31) {
                arr[1] +=
                    d3.event && d3.event.scale
                        ? d3.event.scale / height + 20
                        : initialScale / height + 20;
            } else if (d.properties.code == 34) {

                arr[1] +=
                    d3.event && d3.event.scale
                        ? d3.event.scale / height + 10
                        : initialScale / height + 10;
            }
            return 'translate(' + arr + ')';
        }

        function zoom() {
            projection.translate(d3.event.translate).scale(d3.event.scale);
            states.selectAll('path').attr('d', path);
            labels.attr('transform', translateTolabel);
        }
    }
    
   
    </script>
    <body>
    
        <div id="container">
     <div id="app">
      <div>{{message}}</div>
      <h1>{{description}}</h1>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script>
      var app = new Vue({
        el: '#app',
        data: {
        	message: '안녕하세요 Vue!',
          description: '반갑습니다',
        },
      });
      
      app.description = '개는 훌륭하다';
     
  	
    </script>    

	
        </div>
        
        
    </body>
</html>