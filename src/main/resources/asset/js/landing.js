// Set onclick function
const joinBtn = document.querySelector('.js-join-btn')
joinBtn.addEventListener('click', function() {
    location.replace('../html/login.html');
})

const aboutUsBtn = document.querySelector('.js-about-us-btn')
aboutUsBtn.addEventListener('click', function() {
    location.replace('../html/about_us.html');
})

const contactBtn = document.querySelector('.js-contact-btn')
contactBtn.addEventListener('click', function() {
    location.replace('../html/contact.html');
})

const fbBtn = document.querySelector('.js-fb-btn')
fbBtn.addEventListener('click', function() {
    location.href = 'https://www.facebook.com/UIT.Fanpage/';
})

const instaBtn = document.querySelector('.js-insta-btn')
instaBtn.addEventListener('click', function() {
    location.href = 'https://instagram.com/uituniversity?igshid=YmMyMTA2M2Y=';
})  