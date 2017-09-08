//事件监听，鼠标选中提示信息
  var index=-1;
  document.onkeydown=function(e){
      e=window.event||e;
      switch(e.keyCode){
        case 38: //向上键
          index--;
          changeColor();
          break;
        case 40: //向下键
          index++;
          changeColor();
          break;
        case 13:  //回车
          enterKey(); 
          break;
        default:
          break;
      }
    }
  
  // 改变颜色
   function changeColor(){
       var tipsList=$("#searchTips li");//获取所有的li元素  
        if(index<0){
            index = -1;
            return ;
        }
        if(index>=tipsList.length){
            index = tipsList.length-1;
        }
       for(var i=0;i<tipsList.length;i++){ 
           tipsList[i].className="list-group-item";
       }  
       tipsList[index].className="tipSelected list-group-item";
   }
   //进行搜索，实时提示
    function ajaxSearchTips(){
        var keyword = $("#searchInput").val();
        $("#searchTips").html("");
        $.ajax({
               url:"${pageContext.request.contextPath}/api/news?format=json",
               type:"GET",
               dataType:"json",
               data:{
                "title":keyword
               },
               success:function(news){
                    for (var index in news) {
                        var title = news[index].newsTitle;
                        $("#searchTips").append("<li class='list-group-item'><a  href='#'>"+title+"</a></li>");
                    }
               }
            });
        
    }