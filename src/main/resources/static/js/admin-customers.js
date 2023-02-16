const customerUrl = domain + `/api/v1/customers`;
let currentPage = 0;
let pageSize = 5;
let totalPages;
let search = '';
// Get First Page
getCustomerPaginated(currentPage, pageSize, search);
function getCustomerPaginated(page, size, search) {
    let url;
    if (search == '') {
        url = customerUrl + `?page=${page}&size=${size}&sort=lastModifiedAt,desc`;
    } else {
        url = customerUrl + `?page=${page}&size=${size}&sort=last_modified_at,desc&key=${search}`
    }
    // Call GET request
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            totalPages = data.totalPages
            currentPage = page
            $('#list-customers').html('')
            if (data.content.length == 0) {
                $('#list-customers').html(`<tr><td>Không có dữ liệu</td></tr>`)
            } else {
                data.content.forEach((element, index) => {
                    if ($('#userRoles').text() == 'admin') {
                        $('#list-customers').append(`
                        <tr>
                            <td>${index}</td>
                            <td>${element.name}</td>
                            <td>${element.gender}</td>
                            <td>${element.telephone}</td>
                            <td>${element.rank}</td>
                            <td>${element.accumulatedPoints}</td>
                            <td style="width:15%">
                                <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#updateStaticBackdrop" onclick="showUpdateModal('${element.id}')">Chỉnh sửa</button>
                                <button  class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteStaticBackdrop"  onclick="showDeleteModal('${element.id}','${element.name}')" >Xóa</button>
                            </td>
                        </tr>
                    `)
                    } else {
                        $('#list-customers').append(`
                        <tr>
                            <td>${index}</td>
                            <td>${element.name}</td>
                            <td>${element.gender}</td>
                            <td>${element.telephone}</td>
                            <td>${element.rank}</td>
                            <td>${element.accumulatedPoints}</td>
                            <td style="width:15%">
                                <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#updateStaticBackdrop" onclick="showUpdateModal('${element.id}')">Chỉnh sửa</button>
                            </td>
                        </tr>
                    `)
                    }
                 
                });
                $('#pagination').html('')
                if (data.totalElements > data.size) {
                    let pages = pagination(currentPage, data.totalPages);
                    pages.forEach(element => {
                        if (currentPage + 1 == element) {
                            $('#pagination').append(`<li class="page-item disabled"><a class="page-link" href="#">${element}</a></li>`);

                        } else {
                            $('#pagination').append(`<li class="page-item"><a onclick="getCustomerPaginated(${element - 1}, ${size}, '${search}')" class="page-link" href="#">${element}</a></li>`);
                        }
                    })
                }
            }
        },
        error: function (e) {
            $('#list-customers').html(`<tr><td>Không có dữ liệu</td></tr>`)
        }
    });
}
// Create Customer
$('#add-form').submit(function (e) {
    e.preventDefault();
    $('#add-alert').html('')
    $.ajax({
        url: customerUrl,
        type: 'POST',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {
                name: $('#add-customer-name').val(),
                gender: $('#add-customer-gender').val(),
                telephone: $('#add-customer-telephone').val(),
            }
        ),
        success: function (data, textStatus) {  // Success
            getCustomerPaginated(currentPage, pageSize, '');
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
// Update Customer
$('#update-form').submit(function (e) {
    e.preventDefault();
    $('#update-alert').html('')
    console.log(customerUrl + `/${$('#update-customer-id').val()}`);
    $.ajax({
        url: customerUrl + `/${$('#update-customer-id').val()}`,
        type: 'PUT',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {

                name: $('#update-customer-name').val(),
                telephone: $('#update-customer-telephone').val(),
                gender: $('#update-customer-gender').val(),
                accumulatedPoints: $('#update-customer-accumulated-points').val()
            }
        ),
        success: function (data, textStatus) {  // Success
            getCustomerPaginated(currentPage, pageSize, '');
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
// Delete Customer
$('#btn-delete').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: customerUrl + `/${$('#delete-customer-id').val()}`,
        type: 'DELETE',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            getCustomerPaginated(0, pageSize, '');
        },
        error: function (e) {
        }
    });
})


function showDeleteModal(id, name) {
    $('#delete-modal-message').text(`Xác nhận xóa thể loại: ${name} ?`)
    $('#delete-customer-id').val(id)
}
function showUpdateModal(id) {
    $.ajax({
        url: customerUrl + `/${id}`,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            $('#update-customer-id').val(data.id);
            $('#update-customer-name').val(data.name);
            $('#update-customer-telephone').val(data.telephone);
            $('#update-customer-gender').val(data.gender);
            $('#update-customer-accumulated-points').val(data.accumulatedPoints);

        },
        error: function (e) {
        }
    });

}
$('#search-customer').keyup(function (e) {
    search = $('#search-customer').val()
    getCustomerPaginated(currentPage, pageSize, search);
})