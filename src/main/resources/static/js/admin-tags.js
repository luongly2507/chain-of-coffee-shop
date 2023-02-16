const tagUrl = domain + `/api/v1/tags`;
let currentPage = 0;
let pageSize = 5;
let totalPages;
let search = '';
let tags = [];
let currentBranch = $('#userBranches').text();
if ($('#select-branch')){
    $('#select-branch').val(currentBranch);
    $('#select-branch').change(function(){
        currentBranch =  $('#select-branch').val();
        getBranchPaginated(currentPage, pageSize,currentBranch, search);

    })
}

// Get First Page
getBranchPaginated(currentPage, pageSize,currentBranch, search);
function getBranchPaginated(page, size,branchId, search) {
    let url;
    if (search == '') {
        url = tagUrl + `?page=${page}&size=${size}&branchId=${branchId}&sort=last_modified_at,desc`;
    } else {
        url = tagUrl + `?page=${page}&size=${size}&branchId=${branchId}&sort=last_modified_at,desc&key=${search}`
    }
    // Call GET request
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            totalPages = data.totalPages
            currentPage = page
            $('#list-tags').html('')
            if (data.content.length == 0) {
                $('#list-tags').html(`<tr><td>Không có dữ liệu</td></tr>`)
            } else {
                console.log(data)
                data.content.forEach((element, index) => {
                    console.log(element.tags)
                    $('#list-tags').append(`
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
            $('#list-tags').html(`<tr><td>Không có dữ liệu</td></tr>`)
        }
    });
}
// Create Branch
$('#add-form').submit(function (e) {
    e.preventDefault();
    $('#add-alert').html('')
    $.ajax({
        url: tagUrl,
        type: 'POST',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {
                name: $('#add-tag-name').val(),
                branchID: $('#add-tag-branch').val()
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
    console.log(tagUrl + `/${$('#update-tag-id').val()}`);
    $.ajax({
        url: tagUrl + `/${$('#update-tag-id').val()}`,
        type: 'PUT',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {

                name: $('#update-tag-name').val(),
                status: $('#update-tag-status').val(),
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
    console.log(tagUrl + `/${$('#delete-tag-id').val()}`)
    $.ajax({
        url: tagUrl + `/${$('#delete-tag-id').val()}`,
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
    $('#delete-modal-message').text(`Xác nhận xóa thẻ: ${name} ?`)
    $('#delete-tag-id').val(id)
}
function showUpdateModal(id) {
    $.ajax({
        url: tagUrl + `/${id}`,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            $('#update-tag-id').val(data.id);
            $('#update-tag-name').val(data.name);
            $('#update-tag-address').val(data.address);

            $('#update-tag-description').val(data.description);

        },
        error: function (e) {
        }
    });

}
$('#search-tag').keyup(function (e) {
    search = $('#search-tag').val()
    getBranchPaginated(currentPage, pageSize,currentBranch, search);
})