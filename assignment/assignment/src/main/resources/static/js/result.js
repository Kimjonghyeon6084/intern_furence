function loadData() {
    fetch("/data")
        .then(res => res.json())
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