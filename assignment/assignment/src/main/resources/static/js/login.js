document.getElementById("loginForm").addEventListener("submit", function (e) {

    e.preventDefault();
    let err;

    const id = document.getElementById("id").value;
    const pwd = document.getElementById("pwd").value;

    fetch("/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({id: id, pwd: pwd})
    })
    .then(async res => {
        console.log(res);
        if (res.ok) {
            window.location.href = "/userlist";
        } else {
            let errorMessage = "알 수 없는 오류가 발생했습니다.";
            try {
                const err = await res.json();
                errorMessage = err.message || errorMessage;

                 // 필드별로 나눠 보여주는 로직
                const idError = document.getElementById("idError");
                const pwdError = document.getElementById("pwdError");
                const loginFail = document.getElementById("loginFail");
                idError.textContent = "";
                pwdError.textContent = "";
                loginFail.textContent = "";

                // @valid에 걸려서 넘어온 res에서 loginField를 이용해
                // id or pwd의 input 아래에 해당 메세지 보여줄거다.

                console.log(err);

                if (err.loginField === 'id') {
                    idError.textContent += err.message;
                } else if (err.loginField === 'pwd') {
                    pwdError.textContent += err.message;
                }

                if (err.loginResult === "FAILURE") {
                    loginFail.textContent += err.loginResultMessage;
                }
            } catch (e) {
                alert("JSON 파싱 실패", e);
            }
        }
    })
    .catch(err => {
        alert("서버 오류");
    });
});

