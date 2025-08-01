document.getElementById("uploadForm").onsubmit = function(e) {
            e.preventDefault();
            const formData = new FormData(this);

            fetch("/upload", {
                method: "POST",
                body: formData
            })
            .then(res => res.json())
            .then(result => {
                let html = `<h2>업로드 결과</h2>
                            <p>성공: ${result.successCount}건</p>
                            <p>실패: ${result.errors.length}건</p>`;
                if (result.errors.length > 0) {
                    html += `<h4>실패 내역</h4><ul>`;
                    result.errors.forEach(err => {
                        html += `<li>라인 ${err.lineCount}: ${err.errors}</li>`;
                    });
                    html += `</ul>`;
                }
                html += `<button onclick="loadData()">조회</button>
                         <table id="dataTable">
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
                         <button onclick="goBack()">뒤로가기</button>`;
                document.getElementById("resultArea").innerHTML = html;
            })
            .catch(() => alert("업로드 중 오류 발생!"));
        };

function loadData() {
    fetch("/data")
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector("#dataTable tbody");
            tbody.innerHTML = "";
            data.forEach(row => {
                const date = new Date(row.reg_date);
                const formattedDate = `${date.getFullYear()}년${date.getMonth() + 1}월${date.getDate()}일 ` +
                                      `${date.getHours()}시${date.getMinutes().toString().padStart(2, '0')}분`
                const tr = document.createElement("tr");
                tr.innerHTML = `<td>${row.id}</td>
                                <td>${row.pwd}</td>
                                <td>${row.name}</td>
                                <td>${row.level}</td>
                                <td>${row.desc}</td>
                                <td>${formattedDate}</td>`;
                tbody.appendChild(tr);
            });
        });
}

function goBack(){
    window.location.href = "/upload";
}