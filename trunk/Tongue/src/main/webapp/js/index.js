/**
 * Created by huanghanqian on 17/12/14.
 */
var userName="";
var date="";
var pageNo=1;
var pageSize=6;
var sortName="";
var sortOrder="";
$(function () {
    getDiseases();
})

function show(id){
    window.location.href="detail.html?id="+id;
}

function getDiseases(){
    var postData={};
    if(userName!=""){
        postData.userName=userName;
    }
    if(date!=""){
        postData.date=date;
    }
    if(pageNo!=""){
        postData.pageNo=pageNo;
    }
    if(pageSize!=""){
        postData.pageSize=pageSize;
    }
    if(sortName!=""){
        postData.sortName=sortName;
    }
    if(sortOrder!=""){
        postData.sortOrder=sortOrder;
    }

    $.ajax({
        type: "post",
        url: "http://www.ufengtech.xyz/Tongue/api/disease/getDiseases",
        data: postData,
        //data: {"para": 1},
        cache: false,
        dataType: "json",
        success: function (data, textStatus) {
            var pageNum=data.page.totalPages;
            var pageContent="";
            if(pageNo!=1){
                pageContent+='<li id="before" style="margin-right:5px;">'+
                    '<a href="javascript:pageBefore()">&lt;</a>'+
                    '</li>';
            }
            for(var i=1;i<=pageNum;i++){
                if(i==pageNo){
                    pageContent+='<li class="active" style="margin-right:5px;">'+
                        '<a href="#">'+i+'</a>'+
                        '</li>';
                }
                else{
                    pageContent+='<li style="margin-right:5px;">'+
                        '<a href="javascript:pageChange('+i+')">'+i+'</a>'+
                        '</li>';
                }
            }
            if(pageNum>pageNo){
                pageContent+='<li id="after">'+
                    '<a href="javascript:pageAfter()">&gt;</a>'+
                    '</li>';
            }
            else{
                pageContent+='<div style="width:115px;"></div>';
            }
            $('#pages').html(pageContent);

            var list = data.list;
            var grid_content="";
            var list_content="";
            for (var i = 0; i < list.length; i++) {
                var item=list[i];
                var user=item['userBean'];
                grid_content += '<div class="col-md-4 col-sm-6 col-xs-12 mar-bot">' +
                    '<div class="single-product">' +
                    '<div class="single-product-img">' +
                    '<a href="javascript:show('+item.id+')"><img src="'+item.cover+'" alt=""/></a>' +
                    '<span class="sale-box">' +
                    '<span class="sale">New</span>' +
                    '</span>' +
                    '</div>' +
                    '<div class="single-product-content">' +
                    '<div class="price-box">' +
                    '<span class="price"><a href="javascript:show('+item.id+')">'+user.name+'</a></span>' +
                    '<h5 class="sex" >'+item.createTimeStr+'</h5>' +
                    '</div>' +
                    '<div class="product-action" style="margin-bottom: -5px;">' +
                    ' <button class="btn btn-default add-cart" title="add to cart" onclick="show('+item.id+')">查看详情' +
                    ' </button>' +
                    ' </div>' +
                    ' </div>' +
                    ' </div>' +
                    ' </div>';

                list_content+='<div class="col-md-12 col-sm-12 col-xs-12">'+
                    '<div class="col-md-4 col-sm-4 col-xs-12">'+
                    '<div class="single-product">'+
                    '<div class="single-product-img" style="margin-bottom:10px;">'+
                    '<a href="javascript:show('+item.id+')">'+
                    '<img src="'+item.cover+'" alt="" />'+
                    '</a>'+
                    '<span class="sale-box">'+
                    ' <span class="sale">New</span>'+
                    ' </span>'+
                    '</div>'+
                    '</div>'+
                    ' </div>'+
                    '<div class="col-md-8 col-sm-8 col-xs-12">'+
                    '<div class="single-product">'+
                    ' <div class="single-product-content">'+
                    '<div class="product-title" style="margin-top:6px;">'+
                    '<h5>'+
                    '<a href="javascript:show('+item.id+')" style="font-size: 22px;">'+user.name+'</a>'+
                    '</h5>'+
                    '</div>'+
                    '<div class="man-box">'+
                    '<span class="old-price">'+user.sexStr+'</span><label style="width:15px;"> </label>'+
                    '<span class="old-price">'+user.age+'岁</span>'+
                    ' </div>'+
                    ' <div class="man-box">'+
                    '   <span class="date">'+item.createTimeStr+'</span>'+
                    ' </div>'+
                    '<p class="product-desc">'+item.description+'</p>'+
                    '<div class="product-action">'+
                    ' <button class="button btn btn-default add-cart" title="add to cart" onclick="show('+item.id+')">查看详情</button>'+
                    ' </div>'+
                    ' </div>'+
                    '</div>'+
                    '</div>'+
                    '</div>';
            }
            $('#gried_view').html(grid_content);
            $('#list').html(list_content);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("请求失败！");
        }
    });

}

$("#orderSelect").change(function(){
    var orderValue=$("#orderSelect").val();
    if(orderValue!="none"){
        var arr=orderValue.split(":");
        sortName=arr[0];
        sortOrder=arr[1];
    }
    else{
        sortName="";
        sortOrder="";
    }
    getDiseases();
});

$("#pageSizeSelect").change(function(){
    var sizeValue=$("#pageSizeSelect").val();
    pageSize=sizeValue;
    getDiseases();
});

function getInfo(dateID) {
    date=$('#'+dateID).val();
    getDiseases();
}

function searchName() {
    userName=$('#nameSearch').val();
    getDiseases();
}

function pageChange(pageID){
    pageNo=pageID;
    getDiseases();
}

function pageBefore(){
    pageNo--;
    getDiseases();
}

function pageAfter() {
    pageNo++;
    getDiseases();
}
