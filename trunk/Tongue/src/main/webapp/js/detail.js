/**
 * Created by huanghanqian on 17/12/15.
 */

var sheng_json="";
var sheng_length="";
var id="";

$(function() {

    $
        .ajax({
            type: "get",
            url: "/Tongue/api/category/getChildByName",
            data: {
                "name": "舌苔"
            },
            async: false,
            cache: false,
            dataType: "json",
            success: function (data, textStatus) {
                sheng_json = [];
                var big = data.object.children;
                for (var i = 0; i < big.length; i++) {
                    var bigone = big[i];
                    var json={};
                    json.sheng=bigone.nodeName;
                    json.shi=[];
                    json.ids=[];
                    var sub=bigone.children;
                    for(var j=0;j<sub.length;j++){
                        var two=sub[j];
                        json.shi.push(two.nodeName);
                        json.ids.push(two.id);
                    }
                    sheng_json.push(json);

                }
                // var sheng_json = [ {
                //     sheng : '寒',
                //     shi : [ '表寒', '痰湿' ]
                // }, {
                //     sheng : '热',
                //     shi : [ '湿热', '内火' ]
                // }, {
                //     sheng : '虚',
                //     shi : [ '气虚', '血虚' ]
                // }, {
                //     sheng : '实',
                //     shi : [ '血淤', '食滞' ]
                // } ];
                sheng_length = sheng_json.length;

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("请求失败！");
            }
        });

	var flage = $("#proList").children().get(0);
	if (flage == null) {
		for (var i = 0; i < sheng_length; i++) {
			var sheng = sheng_json[i].sheng;
			$("#proList").append(
					"<li><a href=\"javascript:void 0;\" value=" + sheng + ">"
							+ sheng + "</a></li>");
		}
	}

	$('#proList li a').on(
			'click',
			function(event) {
				var sheng = $(this).attr('value');
				var citys = new Array();
                var ids = new Array();
				$("#proText").html(sheng);
				for (var i = 0; i < sheng_length; i++) {
					if (sheng == sheng_json[i].sheng) {
						citys = sheng_json[i].shi;
                        ids=sheng_json[i].ids;
						break;
					}
				}

				var flage2 = $("#cityList").children().get(0);
				if (flage2 != null) {
					$("#cityList").empty();
					$("#cityText").html("二级分类");
				}
				for (var j = 0; j < citys.length; j++) {
					$("#cityList").append(
							"<li><a href=\"javascript:void 0;\" id=\""+ids[j]+"\" value="
									+ citys[j] + ">" + citys[j] + "</a></li>");
				}

			});

	$("#city").on('click', function(event) {
		$("#cityList li a").on('click', function(event) {
			var city = $(this).attr('value');
            var category= $(this).attr('id');
			$("#cityText").html(city);
            $("#hiddenCategory").val(category);
		});

	});

	$(".addBtn").on('click', function(event) {
		$("#proText").html("一级分类");
		$("#cityList").empty();
		$("#cityText").html("二级分类");
	});

	var reg = new RegExp("(^|&)" + "id" + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	id = unescape(r[2]);

	$.ajax({
		type : "get",
		url : "/Tongue/api/disease/getUserInfo",
		data : {
			"diseaseId" : id
		},
		cache : false,
		dataType : "json",
		success : function(data, textStatus) {
			var user = data.object;
			$('#name').text(user.name);
			$('#sex').text(user.sexStr);
			$('#age').text(user.age);
			$('#job').text(user.job);
			$('#mobile').text(user.mobile);
			$('#history').text(user.history);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求失败！");
		}
	});

	$
			.ajax({
				type : "get",
				url : "/Tongue/api/chat/getChat",
				data : {
					"diseaseId" : id
				},
				cache : false,
				dataType : "json",
				success : function(data, textStatus) {
					var list = data.list;
					var chats = "";
					for (var i = 0; i < list.length; i++) {
						var item = list[i];
						if (item.type == 1) {
							var imgs = "";
							var photos = item.photos;
							for (var j = 0; j < photos.length; j++) {
								imgs += '<div class="test"'
										+ 'style="background: url('
										+ photos[j].photoUrl
										+ ') no-repeat;background-size:100% 100%;">'
										+ '</div>';
							}
							if (photos.length > 0) {
								var categories = item.categories;
								var tags = "";
								var tagText = "";
								for (var j = 0; j < categories.length; j++) {
									tags += '<li class="tag">'
											+ '<a href="javascript:void 0;">'
											+ categories[j][1].nodeName
											+ '</a>'
											+ '<label class="delete" onclick="remove(this,'+item.id+',\''+categories[j][1].id+'\')">x</label>'
											+ '</li>';
									tagText += categories[j][1].nodeName + ",";
								}
								imgs += '<div class="tagtest">'
										+ '<ul class="tags">'
										+ tags
										+ '<button type="button" class="btn btn-default addBtn" data-toggle="modal" id="btn'+item.id
										+'" data-labels="'
										+ tagText
										+ '" data-target="#addModal">'
										+ '<span class="glyphicon glyphicon-plus-sign"></span>'
										+ '</button>' + '</ul>' + '</div>';
							}
							chats += '<div class="row chat">'
									+ '<img class="patient" src="img/patient.png"/>'
									+ imgs
									+ '<div class="test testText t1" style="background: #CCCCCC;">'
									+ '<p>' + item.description + '</p>'
									+ '</div>' + '<label class="time">'
									+ item.photoTimeStr + '</label>' + '</div>';
						} else {
							chats += '<div class="row chat chatright">'
									+ '<div class="test1 testText" style="background: #CCCCCC;">'
									+ '<p>'
									+ item.description
									+ '</p>'
									+ '</div>'
									+ '<img class="doctor" src="img/doctor.png"/>'
									+ '<label class="timeright">'
									+ item.photoTimeStr + '</label>' + '</div>';
						}
					}
					$("#chats").html(chats);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("请求失败！");
				}
			});

});

$('#addModal').on(
		'show.bs.modal',
		function(event) {
        $("#proText").html("一级分类");
        $("#cityList").empty();
        $("#cityText").html("二级分类");
		var button = $(event.relatedTarget)
		var theid=button.attr('id');
		var modal = $(this);
		modal.find('.modal-body #hiddenID').val(theid);
});

$('#addLabelBtn').on('click',function(event) {			       		
	var label = $('#cityText').text();
	if (label == "二级分类") {
		alert("请选择完整的分类");
	} else {
		var theid=$('#hiddenID').val();
        theid=theid.substr(3);
        var button=$("#btn"+theid);
        var category=$('#hiddenCategory').val();
		var having = button.attr("data-labels");
        var arr="";
        if(typeof(having)=="undefined"){
            arr=new Array();
            having="";
        }
        else{
            arr = having.split(",");
        }
		var isOK=true;
		for (var i = 0; i < arr.length - 1; i++) {
			if (arr[i] == label) {
				alert("请勿选择图片已有的分类");
				isOK=false;
				break;
			}
		}
		if (isOK == true) {
            var j = {};
            j.chatId= theid;
            j.categoryId = category;
            var jsonString = JSON.stringify(j);

            $.ajax({
                url: '/Tongue/api/photoCategory/add',
                type: 'post',
                dataType: 'json',
                data: {
                    json: jsonString
                },
                success: function (data,textStatus) {
                    if(data.result==0){
                        alert(data.msg);
                    }
                    else{
                        button.before('<li class="tag">'
                            + '<a href="javascript:void 0;">'
                            + label + '</a>'
                            + '<label class="delete" onclick="remove(this,'+theid+',\''+category+'\')">x</label>'
                            + '</li>');
                        button.attr("data-labels", having + label + ",");
                    }
                }
            });
			$("#addModal").modal('hide');
		}
	}
});

function remove(object,theid,labelID){

	if(confirm("确定删除该标签？")){
        $.ajax({
            url: '/Tongue/api/photoCategory/del',
            type: 'get',
            dataType: 'json',
            data: {
                chatId: theid,
                categoryId:labelID
            },
            success: function (data,textStatus) {
                if(data.result==0){
                    alert(data.msg);
                    return false;
                }
                else{
                    $(object).parent().remove();
                    return true;
                }
            }
        });
	}
}

$('#replyBtn').on('click',function(event) {
    var text=$('#reply').val();
    if(text==null||text==""){
    	alert("回复不能为空");
    }
    else{
        $.ajax({
            url: '/Tongue/api/chat/add',
            type: 'post',
            dataType: 'json',
            data: {
                description: text,
                diseaseId:id
            },
            success: function (data,textStatus) {
                if(data.result==0){
                    alert(data.msg);
                }
                else{
                    $('#chats').append('<div class="row chat chatright">'
                        + '<div class="test1 testText" style="background: #CCCCCC;">'
                        + '<p>'
                        + text
                        + '</p>'
                        + '</div>'
                        + '<img class="doctor" src="img/doctor.png"/>'
                        + '<label class="timeright">'
                        + getNowFormatDate() + '</label>' + '</div>');
                    $('#reply').val("");
                }
            }
        });
    }
});

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var strHour=date.getHours();
    var strMinute=date.getMinutes();
    var strSecond=date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if(strHour>= 0 && strHour <= 9){
        strHour = "0" + strHour;
    }
    if(strMinute>= 0 && strMinute <= 9){
        strMinute = "0" + strMinute;
    }
    if(strSecond>= 0 && strSecond <= 9){
        strSecond = "0" + strSecond;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + strHour + seperator2 + strMinute
            + seperator2 + strSecond;
    return currentdate;
}
