document.getElementById("loginForm").addEventListener("submit", function (e) {

    e.preventDefault();

    const id = document.getElementById("id").value;
    const pwd = document.getElementById("pwd").value;

    fetch("/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({id, pwd})
    })
    .then(async res => {
        //console.log(res);
        if (res.ok) {
            window.location.href = "/userlist";
        } else {
            const err = await res.json();
            //console.log("err json:", err);
            alert(err.message);
            //window.location.href = "/login";
            console.log("login fail")
        }
    })
    .catch(err => {
        alert("서버 오류");
    });
});