const orderUrl = domain + `/api/v1/orders`;
let currentPage = 0;
let pageSize = 5;
let totalPages;
let search = '';
let orders = [];
let currentBranch = $('#userBranches').text();
let ordersTempList =[];

$('.add-order-product').change( function(){
    ordersTempList =[];
    $('#orders-temp-list').html('')
    for (let i = 0 ; i < $('.add-order-product').length; i ++){
        if ( $('#'+$('.add-order-product')[i].id).val() > 0) {
            ordersTempList.push({
                id: $('.add-order-product')[i].id,
                quantity: $('#'+$('.add-order-product')[i].id).val(),
                price: $('#product-price-'+$('.add-order-product')[i].id).text(),
                name: $('#product-name-'+$('.add-order-product')[i].id).text(),
            })
        }
    }
    let total = 0;
    ordersTempList.forEach((element, index)=> {
        total += element.price * element.quantity;
        $('#orders-temp-list').append(`
            <tr>
                <td>${index}</td>
                <td>${element.name}</td>
                <td>${element.price}</td>
                <td>${element.quantity}</td>
                <td>${element.price * element.quantity}</td>
            </tr>
        `)
    })
    $('#orders-temp-list').append(
        `<tr><td class="fw-bold">Tổng cộng: </td><td class="fw-bold" colspan ="4">${total} VNĐ</td></tr>`
    )
    console.log(ordersTempList)
})
if ($('#select-branch')){
    $('#select-branch').val(currentBranch);
    $('#select-branch').change(function(){
        currentBranch =  $('#select-branch').val();
        getBranchPaginated(currentPage, pageSize,currentBranch, search);

    })
}
$('#button-add-order').click(function(){
    $('#add-order-tag').html('');
    $('#add-order-tag').append(`
        <option value="" class="form-control ">Mang về</option>
    `)
    $.ajax({
        url:   domain + `/api/v1/tags` + "/branch/" +  $('#add-order-branch').val() ,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            data.forEach( element => {
                if (element.status == 'Trống')
                $('#add-order-tag').append(`<option class="form-control" value="${element.id}">${element.name}</option>`)
            })
        }});
});
// Get First Page
//getBranchPaginated(currentPage, pageSize,currentBranch, search);
function getBranchPaginated(page, size,branchId, search) {
    let url;
    if (search == '') {
        url = orderUrl + `?page=${page}&size=${size}&branchId=${branchId}&sort=last_modified_at,desc`;
    } else {
        url = orderUrl + `?page=${page}&size=${size}&branchId=${branchId}&sort=last_modified_at,desc&key=${search}`
    }
    // Call GET request
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            totalPages = data.totalPages
            currentPage = page
            $('#list-orders').html('')
            if (data.content.length == 0) {
                $('#list-orders').html(`<tr><td>Không có dữ liệu</td></tr>`)
            } else {
                console.log(data)
                data.content.forEach((element, index) => {
                    console.log(element.orders)
                    $('#list-orders').append(`
                        <tr>
                            <td>${index}</td>
                            <td>${element.name}</td>
                            <td>${element.branch.name}</td>
                            <td>${element.status }</td>
                            <td style="width:15%">
                                <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#updateStaticBackdrop" onclick="showUpdateModal('${element.id}')">Chỉnh sửa</button>
                                <button  class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteStaticBackdrop" onclick="showDeleteModal('${element.id}','${element.name}')" >Xóa</button>
                            </td>
                        </tr>
                    `)
                });
                $('#pagination').html('')
                if (data.totalElements > data.size) {
                    let pages = pagination(currentPage, data.totalPages);
                    pages.forEach(element => {
                        if (currentPage + 1 == element || element == '...') {
                            $('#pagination').append(`<li class="page-item disabled"><a class="page-link" href="#">${element}</a></li>`);

                        } else {
                            $('#pagination').append(`<li class="page-item"><a onclick="getBranchPaginated(${element - 1}, ${size},'${currentBranch}', '${search}')" class="page-link" href="#">${element}</a></li>`);
                        }
                    })
                }
            }
        },
        error: function (e) {
            $('#list-orders').html(`<tr><td>Không có dữ liệu</td></tr>`)
        }
    });
}
// Create Branch
$('#add-form').submit(function (e) {
    e.preventDefault();
    $('#add-alert').html('')
    $.ajax({
        url: orderUrl,
        type: 'POST',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {
                name: $('#add-order-name').val(),
                branchID: $('#add-order-branch').val()
            }
        ),
        success: function (data, textStatus) {  // Success
            getBranchPaginated(currentPage, pageSize,currentBranch, '');
            $('#add-form')[0].reset()
            var myModalEl = document.getElementById('addStaticBackdrop')
            var modal = bootstrap.Modal.getInstance(myModalEl)
            modal.hide()
        },
        error: function (e) {
            $('#add-alert').html(`<div class="alert alert-danger mt-3" role="alert">${e.responseJSON.message}</div>`)
        }
    });
})
// Update Branch
$('#update-form').submit(function (e) {
    e.preventDefault();
    $('#update-alert').html('')
    console.log(orderUrl + `/${$('#update-order-id').val()}`);
    $.ajax({
        url: orderUrl + `/${$('#update-order-id').val()}`,
        type: 'PUT',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {

                name: $('#update-order-name').val(),
                status: $('#update-order-status').val(),
            }
        ),
        success: function (data, textStatus) {  // Success
            getBranchPaginated(currentPage, pageSize,currentBranch,'');
            $('#update-form')[0].reset()
            var myModalEl = document.getElementById('updateStaticBackdrop')
            var modal = bootstrap.Modal.getInstance(myModalEl)
            modal.hide()
        },
        error: function (e) {
            $('#update-alert').html(`<div class="alert alert-danger mt-3" role="alert">${e.responseJSON.message}</div>`)
        }
    });
})
// Delete Branch
$('#btn-delete').click(function (e) {
    e.preventDefault();
    console.log(orderUrl + `/${$('#delete-order-id').val()}`)
    $.ajax({
        url: orderUrl + `/${$('#delete-order-id').val()}`,
        type: 'DELETE',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            console.log(data)
            getBranchPaginated(0, pageSize,currentBranch, '');
        },
        error: function (e) {
            console.log(e)
        }
    });
})  


function showDeleteModal(id, name) {
    $('#delete-modal-message').text(`Xác nhận xóa hóa đơn: ${name} ?`)
    $('#delete-order-id').val(id)
}
function showUpdateModal(id) {
    $.ajax({
        url: orderUrl + `/${id}`,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            $('#update-order-id').val(data.id);
            $('#update-order-name').val(data.name);
            $('#update-order-address').val(data.address);

            $('#update-order-description').val(data.description);

        },
        error: function (e) {
        }
    });

}
$('#search-order').keyup(function (e) {
    search = $('#search-order').val()
    getBranchPaginated(currentPage, pageSize,currentBranch, search);
})