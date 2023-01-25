const categoryUrl = '/api/v1/categories';

showCategory();



//Validation
function addCategoryValidation() {
    if ($('#category-name').val().length < 3 || $('#category-name').val().length > 100) {
        $('#category-name-validation').text('Category name must be greater than 3 and less than 100.');
        return false;
    } else {
        $('#category-name-validation').text('');
    }
    return true;
}
function updateCategoryValidation() {
    if ($('#category-name-update').val().length < 3 || $('#category-name-update').val().length > 100) {
        $('#category-name-update-validation').text('Category name must be greater than 3 and less than 100.');
        return false;
    } else {
        $('#category-name-update-validation').text('');
    }
    return true;
}
// Add Category
function addCategory() {
    if (addCategoryValidation()) { //if Category Validate
        $.ajax({ // Call POST request
            url: categoryUrl,
            type: 'POST',
            contentType: "application/json;charset=utf-16",
            data: JSON.stringify({
                name: $('#category-name').val(),
                description: $('#category-description').val()
            }),
            success: function (data, textStatus) {  // Success
                $('#category-validation').text("Add Category Success.");
                $('#category-name').val('')
                $('#category-description').val('')
                showCategory()
            },
            error: function (e) {
                $('#category-name-validation').text(JSON.parse(e.responseText).message)
            }
        });
    }
}
// Show Category - GET method
function showCategory() {
    $.ajax({ // Call POST request
        url: categoryUrl,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            if (data.length == 0) {
                $('#category-content').html('<td colspan="7">No data.</td>')
            } else {
                $('#category-content').html('');
                data.forEach(element => {
                    $('#category-content').append(`
                    <tr>
                        <td>${element.id}</td>
                        <td style="width:15%">${element.name}</td>
                        <td style="width:25%">${element.description}</td>
                        <td>${new Date(element.lastModifiedAt).getDate()}/${new Date(element.lastModifiedAt).getMonth()}/${new Date(element.lastModifiedAt).getFullYear()}</td>
                        <td>${element.lastModifiedBy}</td>
                        <td class="dropdown">    
                            <button class="btn btn-light dropdown-toggle" type="button" id="dropdown-menu-button-${element.id}" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fas fa-ellipsis-v"></i>
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdown-menu-button-${element.id}">
                                <li><a class="dropdown-item" 
                                        onclick="updateCategory('${element.name}', '${element.id}')"
                                        href="#">Update
                                        </a>
                                </li>
                                <li>
                                    <a  class="dropdown-item" 
                                        href="#" 
                                        onclick="deleteCategory('${element.name}', '${element.id}')"
                                        >Delete 
                                    </a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    `);
                });
            }
        }
    });
}
// Delete Category
function deleteCategory(name, id) {
    // open Modal
    $('#delete-modal-message').text(`Are you sure you want to delete this category called '${name}' ?.`);
    new bootstrap.Modal($('#deleteStaticBackdrop'), {}).show()
    $('#confirm-delete').click(
        function () {
            $.ajax({ // Call POST request
                url: categoryUrl + "/" + id,
                type: 'DELETE',
                contentType: "application/json;charset=utf-16",
                success: function (data, textStatus) {  // Success
                    showCategory()
                }
            });
        }
    );
}

// Update Category
function updateCategory(name, id) {
    // open Modal
    new bootstrap.Modal($('#updateStaticBackdrop'), {}).show()
    $('#confirm-update').click(
        function () {
            if (updateCategoryValidation()){
                $.ajax({ // Call POST request
                    url: categoryUrl + "/" + id,
                    type: 'PUT',
                    contentType: "application/json;charset=utf-16",
                    data: JSON.stringify({
                        name: $('#category-name-update').val(),
                        description: $('#category-description-update').val()
                    }),
                    success: function (data, textStatus) {  // Success
                        showCategory()
                        $('#category-name-update').val('')
                        $('#category-description-update').val('')
                        $('#category-update-success-validation').text('Update Success.')
                    },
                    error: function (e) {
                        $('#category-update-fail-validation').text(JSON.parse(e.responseText).message)     
                    }
                });
            }
           
        }
    );
}