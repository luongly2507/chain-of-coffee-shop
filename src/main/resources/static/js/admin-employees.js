const employeeUrl = `http://localhost:8080/api/v1/employees`;
let currentPage = 0;
let pageSize = 5;
let totalPages;
let search = '';
// Get First Page
getEmployeePaginated(currentPage, pageSize,search);
function getEmployeePaginated(page, size, search) {
    let url;
    if (search == '') {
        url = employeeUrl + `?branchId=${$('#userBranches').text()}&roles=${$('#userRoles').text()}&page=${page}&size=${size}&sort=last_modified_at,desc`;
    } else {
        url = employeeUrl + `?branchId=${$('#userBranches').text()}&roles=${$('#userRoles').text()}&page=${page}&size=${size}&sort=last_modified_at,desc&key=${search}`
    }
    // Call GET request
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            totalPages = data.totalPages
            currentPage = page
            $('#list-employees').html('')
            if (data.content.length == 0) {
                $('#list-employees').html(`<tr><td>Không có dữ liệu</td></tr>`)
            } else {
                data.content.forEach((element, index) => {
                    $('#list-employees').append(`
                        <tr>
                            <td>${index}</td>
                            <td>${element.name}</td>
                            <td>${element.email}</td>
                            <td>${element.telephone}</td>
                            <td>${element.gender}</td>
                            <td>${element.birthday}</td>s
                            <td>${element.address}</td>
                            <td>${element.roles == 'ROLE_EMPLOYEE' ? 'Nhân viên' : 'Quản lý'}</td>
                            <td>${element.branches.name}</td>
                            <td>
                                <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#updateStaticBackdrop" onclick="showUpdateModal('${element.id}')">Chỉnh sửa</button>
                                <button  class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteStaticBackdrop" onclick="showDeleteModal('${element.id}','${element.email}')" >Xóa</button>
                            </td>
                        </tr>
                    `)
                });
                $('#pagination').html('')
                if (data.totalElements > data.size) {
                   
                    let pages = pagination(currentPage, data.totalPages);
                    pages.forEach(element=>{
                        if (currentPage + 1 == element){
                            $('#pagination').append(`<li class="page-item disabled"><a class="page-link" href="#">${element}</a></li>`);

                        } else {
                            $('#pagination').append(`<li class="page-item"><a onclick="getEmployeePaginated(${element-1}, ${size}, '${search}')" class="page-link" href="#">${element}</a></li>`);
                        }
                    })
                }
            }
        },
        error: function (e) {
            $('#list-employees').html(`<tr><td>Không có dữ liệu</td></tr>`)
        }
    });
}
// Create Employee
$('#add-form').submit(function (e) {
    e.preventDefault();
    $('#add-alert').html('')
    $.ajax({
        url: employeeUrl,
        type: 'POST',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {
                email: $('#add-user-email').val(),
                name: $('#add-user-name').val(),
                gender: $('#add-user-gender').val(),
                role: [$('#add-user-role').val()],
                telephone: $('#add-user-telephone').val(),
                address: $('#add-user-address').val(),
                branch: $('#add-user-branch').val(),
                password: $('#add-user-password').val(),
                birthday: $('#add-user-birthday').val()
            }
        ),
        success: function (data, textStatus) {  // Success
            getEmployeePaginated(currentPage, pageSize, '');
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
// Update Employee
$('#update-form').submit(function(e){
    e.preventDefault();
    $('#update-alert').html('')
    $.ajax({
        url: employeeUrl + `/${$('#update-user-id').val()}`,
        type: 'PUT',
        contentType: "application/json;charset=utf-16",
        data: JSON.stringify(
            {
                email: $('#update-user-email').val(),
                name: $('#update-user-name').val(),
                gender: $('#update-user-gender').val(),
                role: [$('#update-user-role').val()],
                telephone: $('#update-user-telephone').val(),
                address: $('#update-user-address').val(),
                password: $('#update-user-password').val(),
                birthday: $('#update-user-birthday').val(),
                branch:  $('#update-user-branch').val()
            }
        ),
        success: function (data, textStatus) {  // Success
            getEmployeePaginated(currentPage, pageSize, '');
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
// Delete Employee
$('#btn-delete').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: employeeUrl + `/${$('#delete-employee-id').val()}`,
        type: 'DELETE',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            getEmployeePaginated(0, pageSize, '');
        },
        error: function (e) {
        }
    });
})


function showDeleteModal(id, email) {
    $('#delete-modal-message').text(`Xác nhận xóa nhân viên có email: ${email} ?`)
    $('#delete-employee-id').val(id)
}
function showUpdateModal(id){
    $.ajax({
        url: employeeUrl + `/${id}`,
        type: 'GET',
        contentType: "application/json;charset=utf-16",
        success: function (data, textStatus) {  // Success
            $('#update-user-id').val(data.id);
            $('#update-user-name').val(data.name);
            $('#update-user-email').val(data.email);
            $('#update-user-address').val(data.address);
            $('#update-user-gender').val(data.gender);
            $('#update-user-birthday').val(data.birthday);
            $('#update-user-telephone').val(data.telephone);
            $('#update-user-branch').val(data.branches.id);
            $('#update-user-role').val(data.roles == 'ROLE_EMPLOYEE' ? 'employee' : 'manager');
        },
        error: function (e) {
        }
    });
   
}
$('#search-employee').keyup(function(e){
    search = $('#search-employee').val()
    getEmployeePaginated(currentPage, pageSize,search);
})