document.querySelector('.js-merging-tbl').innerHTML = localStorage.getItem("merge-tbl-num");

const tbls = document.querySelectorAll('.js-tbl-btn')
const tblNums = document.querySelectorAll('.js-merge-tbl')

// Begin: Hide merging table //
for (const tblNum of tblNums) {
    if (tblNum.innerHTML == localStorage.getItem("merge-tbl-num")) {
        tblNum.parentElement.classList.add('hide');
    }
}
// End: Hide merging table //

// Begin: Select merge table //
let selectingTbl = "";
let mergingTbl = localStorage.getItem("merge-tbl-num");

for (const tbl of tbls) {
    tbl.addEventListener('click', function() {
        tbl.classList.add('selected');
        for (const el of tbls) {
            if (el != tbl) {
                el.classList.remove('selected');
            }
        } 
        selectingTbl = tbl.querySelector('.js-merge-tbl').innerHTML;
    })
}

const confBtn = document.querySelector('.js-confirm-btn')
const cancBtn = document.querySelector('.js-cancel-btn')

confBtn.addEventListener('click', function() {
    localStorage.clear;
    localStorage.setItem("tbl-num", selectingTbl);
    location.replace('../html/order.html');
})

cancBtn.addEventListener('click', function() {
    localStorage.clear;
    localStorage.setItem("tbl-num", mergingTbl);
    location.replace('../html/order.html');
})
// End: Select merge table //