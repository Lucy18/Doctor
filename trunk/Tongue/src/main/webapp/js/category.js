//定义界面元素展开和收起的方式
$(function () {

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
                var contents = "";
                var big = data.object.children;
                for (var i = 0; i < big.length; i++) {
                    var bigone = big[i];
                    var subArr = bigone.children;
                    var sub = "";
                    if (subArr != null) {
                        for (var j = 0; j < subArr.length; j++) {
                            sub += '<li class="parent_li">'
                                + '<div class="item-info second-class" id="1">'
                                + '<span class="root-title" >&nbsp;'
                                + subArr[j].nodeName
                                + '</span>'
                                + '<a href="#" data-toggle="modal" data-target="#modify-modal" data-id="'
                                + subArr[j].id
                                +'" data-parent="'+bigone.id+'" data-name="'+subArr[j].nodeName+'"'
                                + 'class="modify-btn-level-2" style="margin-left:5px;">'
                                + '<span class="glyphicon glyphicon-pencil"></span>'
                                + '</a>'
                                + '<a href="#" data-toggle="modal" data-target="#remove-modal" data-id="'
                                + subArr[j].id
                                + '" class="remove-btn" style="margin-left:5px;">'
                                + '<span class="glyphicon glyphicon-remove"></span>'
                                + '</a>'
                                + '</div>' + '</li>';
                        }
                    }
                    contents += '<li class="parent_li first">'
                        + '<div class="item-info first-class" id="1">'
                        + '<span class="root-title glyphicon  glyphicon glyphicon-minus">&nbsp;'
                        + bigone.nodeName
                        + '</span>'
                        + '<a href="#" data-toggle="modal" data-target="#modify-modal"'
                        + 'data-id="'+bigone.id+'" data-parent="1" data-name="'+bigone.nodeName+'"'
                        + 'class="modify-btn-level-1" style="margin-left:5px;">'
                        + '<span class="glyphicon glyphicon-pencil"></span>'
                        + '</a>'
                        + '<a href="#" data-toggle="modal" data-target="#remove-modal" class="remove-btn" data-id="'
                        + bigone.id
                        + '" style="margin-left:5px;">'
                        + '<span class="glyphicon glyphicon-remove"></span>'
                        + '</a>'
                        + '<a href="#" data-toggle="modal" data-target="#add-modal" data-parent="'
                        + bigone.id
                        + '" class="add-2-btn" style="margin-left:5px;">'
                        + '<span class="glyphicon glyphicon-plus"></span>'
                        + '</a>' + '</div>' + '<ul class="root">' + sub
                        + '</ul>' + '</li>';
                }
                $('#contents').html(contents);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("请求失败！");
            }
        });

    $('.root-title').on(
        'click',
        function (e) {
            var children = $(this).parent('div.item-info').parent(
                'li.parent_li').find('.root');
            if (children.is(":visible")) {
                children.hide('fast');
                $(this).addClass('glyphicon-plus').removeClass(
                    'glyphicon-minus');
            } else {
                children.show('fast');
                $(this).addClass('glyphicon-minus').removeClass(
                    'glyphicon-plus');
                $(children).find('li.parent_li').find('.item-info').find(
                    '.root-title').addClass('glyphicon-minus')
                    .removeClass('glyphicon-plus');
            }
            e.stopPropagation();
        });

});

/** ************************为增删改按钮绑定点击事件*************************************** */

$('.add-2-btn').on('click', function (e) {
    var fatherName = $(this).attr('name');
    var fatherId = $(this).parent('.item-info').attr('id');
    $('#add-level-2-input-fatherName').val(fatherName);
    $('#add-level-2-input-fatherId').val(fatherId);
})

$('.add-3-btn').on('click', function (e) {
    var fatherName = $(this).attr('name');
    var fatherId = $(this).parent('.item-info').attr('id');
    $('#add-level-3-input-fatherName').val(fatherName);
    $('#add-level-3-input-fatherId').val(fatherId);
})

$('.modify-btn-level-1').on('click', function (e) {
    var name = $(this).attr('name');
    var type = $(this).attr('type');
    var id = $(this).parent('.item-info').attr('id');
    $('#modify-level-1-input-id').val(id);
    $('#modify-level-1-input-name').val(name);
    $('#modify-level-1-input-type').val(type);
})

$('.modify-btn-level-2').on('click', function (e) {
    var name = $(this).attr('name');
    var type = $(this).attr('type');
    var id = $(this).parent('.item-info').attr('id');
    $('#modify-level-2-input-id').val(id);
    $('#modify-level-2-input-name').val(name);
    $('#modify-level-2-input-type').val(type);
})

$('.modify-btn-level-3').on('click', function (e) {
    var name = $(this).attr('name');
    var money = $(this).attr('money');
    var money_number = Number(money);
    var localId = $(this).attr('localId');
    var id = $(this).parent('.item-info').attr('id');
    $('#modify-level-3-input-id').val(id);
    $('#modify-level-3-input-name').val(name);
    $('#modify-level-3-input-money').val(money_number);
    $('#modify-level-3-input-localId').val(localId);
})


$('.remove-btn').on('click', function (e) {
    var id = $(this).parent('.item-info').attr('id');
    $('#remove-input-id').val(id);
    var className = $(this).parent('.item-info').attr("class");
    var array = className.split(/[ ]/);
    var level = array[1];
    $('#remove-input-level').val(level);

})

/** **************以下为Ajax交互方法***************************** */


// 修改一级分类
$('#modify-level-1-submit').on('click', function (e) {
    var id = $('#hiddenThisId').val();
    var name = $('#modify-level-1-input-name').val();
    var parent=$('#hiddenThisParentId').val();

    if (id == "" || name == "") {
        alert("请填入完整信息")
        return;
    }

    var j = {};
    j.id= id;
    j.pid = parent;
    j.node_name = name;
    var jsonString = JSON.stringify(j);

    $.ajax({
        url: '/Tongue/api/category/update',
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
                window.location.reload();
            }
        }
    });
    $('#modify-modal').modal('hide');
})


$('#remove-submit').on('click', function (e) {
    var id = $('#hiddenId').val();

    $.ajax({
        url: '/Tongue/api/category/del',
        type: 'post',
        dataType: 'json',
        data: {
            id: id
        },
        success: function (data,textStatus) {
        	 if(data.result==0){
             	alert(data.msg);
             }
             else{
                window.location.reload();
             }
        }
    });
    $('#remove-modal').modal('hide');
})

$('#add-level-1-submit').on('click', function () {
    var name = $('#add-level-1-input-name').val();

    if (name == "") {
        alert("请填入完整信息")
        return;
    }
    var parent = $('#hiddenParent').val();
    var j = {};
    j.pid = parent;
    j.node_name = name;
    var jsonString = JSON.stringify(j);

    $.ajax({
        url: '/Tongue/api/category/add',
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
                window.location.reload();
             }
        }
    });
    $('#add-modal').modal('hide');
})

$('#add-modal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var parent = button.data('parent');
    var modal = $(this);
    modal.find('.modal-body #hiddenParent').val(parent);
});

$('#remove-modal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var id = button.data('id');
    var modal = $(this);
    modal.find('.modal-body #hiddenId').val(id);
});

$('#modify-modal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var id = button.data('id');
    var parent = button.data('parent');
    var name = button.data('name');
    var modal = $(this);
    modal.find('.modal-body #hiddenThisId').val(id);
    modal.find('.modal-body #hiddenThisParentId').val(parent);
    modal.find('.modal-body #hiddenName').val(name);
    modal.find('.modal-body #modify-level-1-input-name').val(name);
});