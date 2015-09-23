var data;

$.getJSON("bargraph_data.json", function(json) {
    data = json;
});


google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
    var stacked = document.getElementById('stacked');
    var table = google.visualization.arrayToDataTable(data.counts);
//    removes column with total
    table.removeColumn(1);
    var options = {
        title: 'Diagnosis age dist. keywords "'+data.keyword+'"',
        vAxis: {title: 'Counts'},
        hAxis: {title: 'Age'},
        chartArea:{left:100, top:50,width:'80%',height:'80%'},
        bar: {groupWidth: "85%"},
        legend : {position: 'top', textStyle: { fontSize: 12}},
        isStacked: stacked.checked
    };
    if (data.option == 'counts'){
        options.title= 'Diagnosis counts keywords "'+data.keyword+'"';
        options.chartArea.left = 700;
        options.vAxis.title = 'Diagnosis desc.';
        options.hAxis.title = 'counts';
        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
        chart.draw(table, options);
    }
    else{
        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(table, options);
    }
}

function perform_query(){
    var keyword = document.getElementById('query_keyword').value;
    var select = document.getElementById('query_type');
    jQuery.ajax({
         type: "GET",
        url: "bargraph/query",
         data: {keyword : keyword, option: select.value},
         contentType: "application/json; charset=utf-8",
         dataType: "json",
         success: function (d, status, jqXHR) {
            data = d;
            drawChart();
         },

         error: function (jqXHR, status) {           
              // error handler
         }
    });
}

$("#query_keyword").keyup(function(event){
    if(event.keyCode == 13){
         perform_query();
    }
});

var button = document.getElementById('submit_button');
button.onclick=perform_query;

var stacked = document.getElementById('stacked');
stacked.onclick=drawChart;

var select = document.getElementById('query_type');
select.onchange=perform_query
