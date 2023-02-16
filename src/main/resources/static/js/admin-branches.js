const branchUrl = domain + `/api/v1/branches`;
let currentPage = 0;
let pageSize = 5;
let totalPages;
let search = '';
let tags = [];

// Get First Page
getBranchPaginated(currentPage, pageSize, search);
function getBranchPaginated(page, size, search) {
    let url;
    if (search == '') {
        url = branchUrl + `?page=${page}&size=${size}&sort=lastModifiedAt,desc`;
    } else {
        url = branchUrl + `?page=${page}&size=${size}&sort=last_modified_at,desc&key=${search}`
    }
    // Call GET request
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            totalPages = data.totalPages
            currentPage = page
            $('#list-branches').html('')
            if (data.content.length == 0) {
                $('#list-branches').html(`<tr><td>Không có dữ liệu</td></tr>`)
            } else {
                console.log(data)
                data.content.forEach((element, index) => {
                    console.log(element.tags)
                    $('#list-branches').append(`
                        <tr>
                            <td>${index}</td>
                            <td>${element.name}</td>
                            <td>${element.address}</td>
                            <td>${element.description }</td>
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
                            $('#pagination').append(`<li class="page-item"><a onclick="getBranchPaginated(${element - 1}, ${size}, '${search}')" class="page-link" href="#">${element}</a></li>`);
                        }
                    })
                }
            }
        },
        error: function (e) {
            $('#list-branches').html(`<tr><td>Không có dữ liệu</td></tr>`)
        }
    });
}
// Create Branch
$('#add-form').submit(function (e) {
    e.preventDefault();
    $('#add-alert').html('')
    $.ajax({
        url: branchUrl,
        type: 'POST',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {
                name: $('#add-branch-name').val(),
                address: $('#add-branch-address').val(),
                description: $('#add-branch-description').val(),
                tags: tags
            }
        ),
        success: function (data, textStatus) {  // Success
            getBranchPaginated(currentPage, pageSize, '');
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
    console.log(branchUrl + `/${$('#update-branch-id').val()}`);
    $.ajax({
        url: branchUrl + `/${$('#update-branch-id').val()}`,
        type: 'PUT',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {

                name: $('#update-branch-name').val(),
                description: $('#update-branch-description').val(),
                address:  $('#update-branch-address').val(),
            }
        ),
        success: function (data, textStatus) {  // Success
            getBranchPaginated(currentPage, pageSize, '');
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
    $.ajax({
        url: branchUrl + `/${$('#delete-branch-id').val()}`,
        type: 'DELETE',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            getBranchPaginated(0, pageSize, '');
        },
        error: function (e) {
        }
    });
})


function showDeleteModal(id, name) {
    $('#delete-modal-message').text(`Xác nhận xóa chi nhánh: ${name} ?`)
    $('#delete-branch-id').val(id)
}
function showUpdateModal(id) {
    $.ajax({
        url: branchUrl + `/${id}`,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            $('#update-branch-id').val(data.id);
            $('#update-branch-name').val(data.name);
            $('#update-branch-address').val(data.address);

            $('#update-branch-description').val(data.description);

        },
        error: function (e) {
        }
    });

}
$('#search-branch').keyup(function (e) {
    search = $('#search-branch').val()
    getBranchPaginated(currentPage, pageSize, search);
})