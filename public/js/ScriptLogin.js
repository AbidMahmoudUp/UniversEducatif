const wrapper = document.querySelector('.wrapper');
const RegisterLink = document.querySelector('.register');
const LoginLink = document.querySelector('.login-link');
const LoginLink1 = document.querySelector('.login-link1');
const LoginLink2 = document.querySelector('.login-link2');
const LoginLink3 = document.querySelector('.login-link3');
const ForgetPasswordLink = document.querySelector('.forget');
const sendLink=document.querySelector('.send-link');
const back = document.querySelector('.goBack');

const tryy=document.getElementById('first-element');
RegisterLink.onclick=()=>{
    wrapper.classList.add('active');
}

LoginLink.onclick=()=>{
    wrapper.classList.remove('active');
}

LoginLink1.onclick=()=>{
    wrapper.classList.remove('forget');
}


LoginLink2.onclick=()=>{
    wrapper.classList.remove('sending');
}


LoginLink3.onclick=()=>{
    wrapper.classList.remove('update-password');
}


ForgetPasswordLink.onclick=()=>{
    wrapper.classList.add('forget');
}




