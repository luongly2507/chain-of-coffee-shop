function onTblClick(tbl_num) {
    localStorage.setItem('tbl-num', tbl_num);
    location.replace('../html/order.html');
}

document.querySelector('.report-btn').addEventListener('click', function() {
    location.replace('../html/report.html');
})

document.querySelector('.logout-btn').addEventListener('click', function() {
    
})