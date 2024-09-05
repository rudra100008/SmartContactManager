function toggleNavbar() {
    const verticalNavbar = document.querySelector('nav.vertical');
    const mainContent=document.querySelector('.main-content');
    const body = document.body;

    verticalNavbar.classList.toggle('open');
    verticalNavbar.classList.toggle('active');
    body.classList.toggle('navbar-open');

    if (verticalNavbar.classList.contains('open')) {
        mainContent.style.marginLeft='100px';
    } else {
        mainContent.style.marginLeft='200px';
    }
}
function redirectToSignUp()
{
    windoww.location.href="http://localhost:8080/home/signup";
}
function redirectToLogin()
{
    window.location.href="http://localhost:8080/home/login";
}