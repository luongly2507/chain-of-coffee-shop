const productUrl = domain + `/api/v1/products`;
let currentPage = 0;
let pageSize = 5;
let totalPages;
let search = '';
// Get First Page
getProductPaginated(currentPage, pageSize, search);

function getProductPaginated(page, size, search) {
    let url;
    if (search == '') {
        url = productUrl + `?page=${page}&size=${size}&sort=lastModifiedAt,desc`;
    } else {
        url = productUrl + `?page=${page}&size=${size}&sort=lastModifiedAt,desc&key=${search}`
    }
    // Call GET request
    $.ajax({
        url: url,
        type: 'GET',
        async: false,
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            totalPages = data.totalPages
            currentPage = page
            $('#list-products').html('')
            if (data.content.length == 0) {
                $('#list-products').html(`<tr><td>Không có dữ liệu</td></tr>`)
            } else {
                data.content.forEach((element, index) => {
                    $('#list-products').append(`
                        <tr>
                            <td class="align-middle">${index}</td>
                            <td style="width: 10%"><img src="/img/upload/${element.image}" class="rounded align-middle" height="120px"/></td>
                            <td class="align-middle">${element.name}</td>
                            <td class="align-middle">${element.category.name}</td>
                            <td class="align-middle">${element.price}</td>
                            <td class="align-middle">${element.description}</td>
                            <td class="align-middle text-center " style="width:15%">
                                <button  class="btn btn-info" data-bs-toggle="modal" data-bs-target="#updateStaticBackdrop" onclick="showUpdateModal('${element.id}')">Chỉnh sửa</button>
                                <button  class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteStaticBackdrop" onclick="showDeleteModal('${element.id}','${element.name}')" >Xóa</button>
                            </td>
                        </tr>
                    `)
                });
                $('#pagination').html('')
                if (data.totalElements > data.size) {
                    let pages = pagination(currentPage, data.totalPages);
                    pages.forEach(element => {
                        if (currentPage + 1 == element) {
                            $('#pagination').append(`<li class="page-item disabled"><a class="page-link" href="#">${element}</a></li>`);

                        } else {
                            $('#pagination').append(`<li class="page-item"><a onclick="getProductPaginated(${element - 1}, ${size}, '${search}')" class="page-link" href="#">${element}</a></li>`);
                        }
                    })
                }
            }
        },
        error: function (e) {
            $('#list-products').html(`<tr><td>Không có dữ liệu</td></tr>`)
        }
    });
}
// Create Product
$('#add-form').submit(function (e) {
    e.preventDefault();
    $('#add-alert').html('')
    $.ajax({
        url: productUrl,
        type: 'POST',
        processData: false,
        contentType: false,
        async: false,
        data: new FormData( this ),
        success: function (data, textStatus) {  // Success
           
            $('#add-form')[0].reset()
            var myModalEl = document.getElementById('addStaticBackdrop')
            var modal = bootstrap.Modal.getInstance(myModalEl)
            modal.hide()
            setTimeout(function(){
                getProductPaginated(currentPage, pageSize, '');
            },1500)        },
        error: function (e) {
            $('#add-alert').html(`<div class="alert alert-danger mt-3" role="alert">${e.responseJSON.message}</div>`)
        }
    });
})
// Update Product
$('#update-form').submit(function (e) {
    e.preventDefault();
    $('#update-alert').html('')
    $.ajax({
        url: productUrl + `/${ $('#update-product-id').val()}`,
        type: 'PUT',
        processData: false,
        contentType: false,
        async: false,
        data: new FormData( this ),
        success: function (data, textStatus) {  // Success
            $('#update-form')[0].reset()
            var myModalEl = document.getElementById('updateStaticBackdrop')
            var modal = bootstrap.Modal.getInstance(myModalEl)
            modal.hide()
            setTimeout(function(){
                getProductPaginated(currentPage, pageSize, '');
            },1500)
        },
        error: function (e) {
            $('#update-alert').html(`<div class="alert alert-danger mt-3" role="alert">${e.responseJSON.message}</div>`)
        }
    });
})
// Delete Product
$('#btn-delete').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: productUrl + `/${$('#delete-product-id').val()}`,
        type: 'DELETE',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            getProductPaginated(0, pageSize, '');
        },
        error: function (e) {
        }
    });
})


function showDeleteModal(id, name) {
    $('#delete-modal-message').text(`Xác nhận xóa sản phẩm: ${name} ?`)
    $('#delete-product-id').val(id)
}
function showUpdateModal(id) {
    $.ajax({
        url: productUrl + `/${id}`,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            $('#update-product-id').val(data.id);
            $('#update-product-name').val(data.name);
            $('#update-product-price').val(data.price);
            $('#update-product-category').val(data.category.id);
            $('#update-product-image').attr("src","/img/upload/"+data.image);
            $('#update-product-description').val(data.description);

        },
        error: function (e) {
        }
    });

}
$('#search-product').keyup(function (e) {
    search = $('#search-product').val()
    getProductPaginated(currentPage, pageSize, search);
})