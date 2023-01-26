renderCategory(search, currentPage);
function renderCategory(search, page) {
    currentPage = page;
    let url = search == null ? categoriesEndpoint + "/page?size=" + pageSize + "&page=" + page + "&sort=lastModifiedAt,desc" : categoriesEndpoint + "/search?key=" + search + "&size=" + pageSize + "&page=" + page + "&sort=last_modified_at,desc"
    console.log(url)
    $.ajax({ // Call GET request
        url: url,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            console.log(data)
            if (data.content.length == 0) {
                $('#categories-list').html(`
                    <tr>
                        <td colspan="6">
                            No data
                        </td>
                    </tr>
                `)
            } else {
                $('#categories-list').html('')
                var index = (data.number + 1) * pageSize - (pageSize - 1);
                data.content.forEach((element) => {
                    $('#categories-list').append(`
                        <tr>
                            <td>${index}</td>
                            <td>${element.name}</td>
                            <td class="w-25">${element.description}</td>
                            <td>${dateTimeFormatter(element.createdAt)}</td>
                            <td>${dateTimeFormatter(element.lastModifiedAt)}</td>.
                            <td>${element.createdBy}</td>
                            <td>${element.lastModifiedBy}</td>
                            <td>
                                <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#updateCategoryStaticBackdrop" onclick="updateCategory('${element.id}','${element.name}','${element.description}')">Update</button>
                                <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteCategoryStaticBackdrop" onclick="deleteCategory('${element.id}','${element.name}')">Delete</button>
                            </td>
                        </tr>
                    `);
                    index++;
                });
                renderCategoryPagination(data.totalPages, data.number + 1);
            }
        }
    });
}
function renderCategoryPagination(totalSize, pageNo) {
    let rangeWithDots = pagination(pageNo, totalSize);
    $('#category-pagination').html('')
    rangeWithDots.forEach((page) => {
        $('#category-pagination').append(`
            <li class="page-item ${page == pageNo ? 'disabled' : ''}"><a class="page-link" onclick="renderCategory(${search},${page - 1})" href="#">${page}</a></li>
        `)
    })
}

function addCategory() {
    if ($('#add-category-name-input').val().length < 3) {
        $('#category-create-validation').removeClass('text-success');
        $('#category-create-validation').addClass('text-danger');
        $('#category-create-validation').text('The name has a minimum length of 3 characters.');
    } else {
        $.ajax({ // Call GET request
            url: categoriesEndpoint,
            type: 'POST',
            contentType: "application/json;charset=utf-16",
            data: JSON.stringify({
                name: $('#add-category-name-input').val(),
                description: $('#add-category-description-input').val()
            }),
            success: function (data, textStatus) {  // Success
                $('#category-create-validation').removeClass('text-danger');
                $('#category-create-validation').addClass('text-success');
                $('#category-create-validation').text('Add success.');
                $('#add-category-name-input').val('');
                $('#add-category-description-input').val('')
                renderCategory(search, 0)
            },
            error: function (e) {
                $('#category-create-validation').removeClass('text-success');
                $('#category-create-validation').addClass('text-danger');
                $('#category-create-validation').text(JSON.parse(e.responseText).message)
            }

        });
    }
}

function searchCategory(key) {
    search = key;
    if (key == '') {
        search = null;
    }
    renderCategory(search, 0)
}
function deleteCategory(id, name) {
    $('#delete-category-modal-message').text("Are you sure delete category named '" + name + "'");
    $('#delete-category-modal-confirm').unbind('click');
    $('#delete-category-modal-confirm').click(function () {
        $.ajax({
            url: categoriesEndpoint + "/" + id,
            type: 'DELETE',
            contentType: "application/json;charset=utf-16",
            success: function (data, textStatus) {  // Success
                renderCategory(search, 0)
            }
        });
    })
}
function updateCategory(id, name, description) {
    $('#update-category-name-input').val(name)
    $('#update-category-description-input').val(description)
    $('#category-update-validation').text('');
    console.log(id);
    $('#update-category-modal-confirm').unbind('click');
    $('#update-category-modal-confirm').click(function () {
        if ($('#update-category-name-input').val().length < 3) {
            $('#category-update-validation').removeClass('text-success');
            $('#category-update-validation').addClass('text-danger');
            $('#category-update-validation').text('The name has a minimum length of 3 characters.');
        } else {
            console.log(id);
            $.ajax({
               
                url: categoriesEndpoint + "/" + id,
                type: 'PUT',
                contentType: "application/json;charset=utf-16",
                data: JSON.stringify({
                    name: $('#update-category-name-input').val(),
                    description: $('#update-category-description-input').val()
                }),
                success: function (data, textStatus) {  // Success
                    $('#category-update-validation').removeClass('text-danger');
                    $('#category-update-validation').addClass('text-success');
                    $('#category-update-validation').text('Update success.');
                    renderCategory(search, 0)
                },
                error: function (e) {
                    $('#category-update-validation').removeClass('text-success');
                    $('#category-update-validation').addClass('text-danger');
                    $('#category-update-validation').text(JSON.parse(e.responseText).message)
                }
            });
        }
    })
}
