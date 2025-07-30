function createUserListView(targetCell) {
   // 1. attachHTML로 래퍼 div만 넣기!
        const html = `
          <div id="userlist-wrapper" style="width:100%;height:100%;">
            <div id="userlist-search"></div>
            <div id="userlist-grid" style="margin-top:12px;"></div>
            <div id="userlist-pagination" style="margin-top:16px;text-align:center;"></div>
          </div>
        `;
        targetCell.attachHTML(html);

        // 2. 각 영역에 dhtmlx 컴포넌트 mount
        var searchForm = new dhx.Form("userlist-search", {
            rows: [
                {
                    cols: [
                        { type: "input", name: "id", placeholder: "ID", width: 80 },
                        { type: "input", name: "name", placeholder: "이름", width: 80 },
                        { type: "input", name: "level", placeholder: "레벨", width: 60 },
                        { type: "input", name: "desc", placeholder: "설명", width: 100 },
                        { type: "datepicker", name: "regDate", placeholder: "가입일", dateFormat: "%Y-%m-%d", width: 120 },
                        { type: "button", name: "search", text: "조회", color: "primary", size: "medium" },
                        { type: "button", name: "reset", text: "초기화", color: "secondary", size: "medium" }
                    ]
                }
            ]
        });

        var grid = new dhx.Grid("userlist-grid", {
            columns: [
                { id: "id", header: [{ text: "ID" }] },
                { id: "name", header: [{ text: "이름" }] },
                { id: "level", header: [{ text: "레벨" }] },
                { id: "desc", header: [{ text: "설명" }] },
                { id: "regDate", header: [{ text: "가입일" }] }
            ],
            autoWidth: true,
            resizable: true,
            height: 442,
            data: []
        });

// Pagination 위젯 mount
    let pageSize = 10; // 기본값, 필요시 서버에서 받아온 걸로 setPageSize 가능
    let totalElements = 0;
    console.log(document.getElementById("userlist-pagination")); // null이면 100% 이게 원인!

    let paginationWidget = new dhx.Pagination("userlist-pagination", {
        pageSize: pageSize,
        total: 0,
        page: 0
    });

    // --- 페이지 변경 이벤트: 반드시 여기서 fetch! ---
    paginationWidget.events.on("change", function(page) {
        fetchUserList(page, lastSearchParams);
    });

    // --- 데이터 요청/페이징 처리 ---
    let lastSearchParams = null;
    function fetchUserList(page = 0, params = null) {
        let url = `/user/list/${page}`;
        if (params) {
            const search = new URLSearchParams(params);
            if (search.toString()) url += "?" + search.toString();
        }
        fetch(url)
            .then(res => res.json())
            .then(data => {
                grid.data.parse(data.content || []);
                // 서버 응답의 totalElements, number 등으로 페이지네이션 갱신!
                paginationWidget.setTotal(data.totalElements || 0);
                paginationWidget.setPage(data.number || 0);
                // 필요하면 pageSize도 갱신 (여기선 10 고정)
            })
            .catch(() => {
                grid.data.parse([]);
                paginationWidget.setTotal(0);
                alert("데이터 로드 실패");
            });
    }

    // 검색/초기화 버튼 이벤트 바인딩
    searchForm.events.on("click", function(name) {
        if (name === "search") {
            const values = searchForm.getValue();
            lastSearchParams = values;
            fetchUserList(0, values); // "조회"시 첫 페이지부터
        }
        if (name === "reset") {
            searchForm.clear();
            lastSearchParams = null;
            grid.data.parse([]);
            paginationWidget.setTotal(0);
        }
    });

}
