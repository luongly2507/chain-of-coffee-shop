const categoryUrl = `http://localhost:8080/api/v1/categories`;
let currentPage = 0;
let pageSize = 5;
let totalPages;
let search = '';
// Get First Page
getCategoryPaginated(currentPage, pageSize, search);
function getCategoryPaginated(page, size, search) {
    let url;
    if (search == '') {
        url = categoryUrl + `?page=${page}&size=${size}&sort=lastModifiedAt,desc`;
    } else {
        url = categoryUrl + `?page=${page}&size=${size}&sort=last_modified_at,desc&key=${search}`
    }
    // Call GET request
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            totalPages = data.totalPages
            currentPage = page
            $('#list-categories').html('')
            if (data.content.length == 0) {
                $('#list-categories').html(`<tr><td>Không có dữ liệu</td></tr>`)
            } else {
                data.content.forEach((element, index) => {
                    $('#list-categories').append(`
                        <tr>
                            <td>${index}</td>
                            <td>${element.name}</td>
                            <td>${element.description}</td>
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
                        if (currentPage + 1 == element) {
                            $('#pagination').append(`<li class="page-item disabled"><a class="page-link" href="#">${element}</a></li>`);

                        } else {
                            $('#pagination').append(`<li class="page-item"><a onclick="getCategoryPaginated(${element - 1}, ${size}, '${search}')" class="page-link" href="#">${element}</a></li>`);
                        }
                    })
                }
            }
        },
        error: function (e) {
            $('#list-categories').html(`<tr><td>Không có dữ liệu</td></tr>`)
        }
    });
}
// Create Category
$('#add-form').submit(function (e) {
    e.preventDefault();
    $('#add-alert').html('')
    $.ajax({
        url: categoryUrl,
        type: 'POST',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {
                name: $('#add-category-name').val(),
                description: $('#add-category-description').val()
            }
        ),
        success: function (data, textStatus) {  // Success
            getCategoryPaginated(currentPage, pageSize, '');
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
// Update Category
$('#update-form').submit(function (e) {
    e.preventDefault();
    $('#update-alert').html('')
    console.log(categoryUrl + `/${ $('#update-category-id').val()}`);
    $.ajax({
        url: categoryUrl + `/${ $('#update-category-id').val()}`,
        type: 'PUT',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {

                name: $('#update-category-name').val(),
                description: $('#update-category-description').val()

            }
        ),
        success: function (data, textStatus) {  // Success
            getCategoryPaginated(currentPage, pageSize, '');
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
// Delete Category
$('#btn-delete').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: categoryUrl + `/${$('#delete-category-id').val()}`,
        type: 'DELETE',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            getCategoryPaginated(0, pageSize, '');
        },
        error: function (e) {
        }
    });
})


function showDeleteModal(id, name) {
    $('#delete-modal-message').text(`Xác nhận xóa thể loại: ${name} ?`)
    $('#delete-category-id').val(id)
}
function showUpdateModal(id) {
    $.ajax({
        url: categoryUrl + `/${id}`,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            $('#update-category-id').val(data.id);
            $('#update-category-name').val(data.name);
            $('#update-category-description').val(data.description);

        },
        error: function (e) {
        }
    });

}
$('#search-category').keyup(function (e) {
    search = $('#search-category').val()
    getCategoryPaginated(currentPage, pageSize, search);
})