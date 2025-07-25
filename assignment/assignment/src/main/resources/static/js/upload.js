document.getElementById("uploadForm").onsubmit = function(e) {
            e.preventDefault();
            const formData = new FormData(this);

            fetch("/upload", {
                method: "POST",
                body: formData
            })
            .then(res => {
                if (!res.ok) {
                    throw res;
                }
                return res.json();
            })
            .then(result => {
                let html = `<h2>업로드 결과</h2>
                            <br/>
                            <p>성공: ${result.successCount}건</p>
                            <p>실패: ${result.errors.length}건</p>
                            <br/>`;
                if (result.errors.length > 0) {
                    html += `<h4>실패 내역</h4><ul>`;
                    result.errors.forEach(err => {
                    console.log("err : ", err)
                        html += `<li>라인 ${err.lineCount}: ${err.errors}</li>`;
                    });
                    html += `</ul>`;
                }
                html += `<div>
                        <button onclick="loadData()" id="selectbutton">조회</button>
                        <button onclick="goBack()" id="gobackbutton">뒤로가기</button>
                     <div/>
                     <table id="dataTable" style = "display: none;">
                        <thead>
                            <tr>
                                <th>id</th>
                                <th>pwd</th>
                                <th>name</th>
                                <th>level</th>
                                <th>desc</th>
                                <th>reg_date</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                     </table>
                     `;
                document.getElementById("resultArea").innerHTML = html;

                const element   = document.getElementById("selectbutton");
                const dataTable = document.getElementById("dataTable");
                element.addEventListener("click", () => {
                    dataTable.style.display = "block";
                })
            })
            .catch(async error => {
                const err = await error.json();
                console.log("error : ", err)
                alert(err.message);
            })
        };

function loadData() {
    fetch("/data")
        .then(response => response.json())
        .then(data => {
        console.log("data : ", data)
            const tbody = document.querySelector("#dataTable tbody");
            tbody.innerHTML = "";
            data.forEach(row => {
                const date = new Date(row.regDate);
                const formattedDate = `${date.getFullYear()}년${date.getMonth() + 1}월${date.getDate()}일 ` +
                                      `${date.getHours()}시${date.getMinutes().toString().padStart(2, '0')}분`
                const tr = document.createElement("tr");
                tr.innerHTML = `<td>${row.id}</td>
                                <td>${row.pwd}</td>
                                <td>${row.name}</td>
                                <td>${row.level}</td>
                                <td>${row.desc ?? '-'}</td>
                                <td>${formattedDate}</td>`;
                tbody.appendChild(tr);
            });
        });
}

function goBack(){
    window.location.href = "/upload";
}