// Begin: Get date //
const curDate = new Date();
if ((curDate.getMonth() + 1) < 10) {
    document.getElementById('js-bill-date').innerHTML = curDate.getDate() + '/0' + (curDate.getMonth() + 1) + '/' + curDate.getFullYear();
}
else {
    document.getElementById('js-bill-date').innerHTML = curDate.getDate() + '/' + (curDate.getMonth() + 1) + '/' + curDate.getFullYear();
}
// End: Get date //

// Begin: Get table number //
document.getElementById('js-tbl-num').innerHTML = localStorage.getItem('tbl-num');
// End: Get table number //

// Begin: Set button click event //
document.querySelector('.cancel-btn').addEventListener('click', function() {
    location.replace('../html/home.html');
})

document.querySelector('.merge-btn').addEventListener('click', function() {
    location.replace('../html/merge.html');
})

document.querySelector('.pay-btn').addEventListener('click', function() {
    location.replace('../html/pay_cash.html');
})

document.querySelector('.discount-btn').addEventListener('click', function() {
    
})
// End: Set button click event //

