// 1. 전체 레이아웃 생성
const createLayout = () => {
    layout = new dhx.Layout("fileuploadform", {
        rows: [
            {
                id: "fileuploadContent",
                height: "content"
            },
            {
                id: "fileuploadResultContent",
                height: "content"
            },
            {
                id: "fileuploadResultGridContent",
                css: "userlistgrid"
            }
        ]
    });
};

// 2. 파일업로드 폼 생성
const createFileuploadForm = () => {
    fileuploadForm = new dhx.Form(null, {
        css: "uploadForm",
        padding: 0,
        rows: [
            {
                name: "fileLabel",
                type: "container",
                css: "fileLabel",
                html: `
                    <label id="fileLabel" for="fileupload" class="dhx_label">파일업로드</label>
                `
            },
            {
                name: "uploadInput",
                type: "container",
                css: "fileuploadInput",
                html: `
                    <input type="file" id="fileupload" accept=".dbfile"/>
                `
            },
            {
                cols: [
                    {
                        name: "uploadFileName",
                        type: "text",
                        readonly: true,
                        css: "fileName",
                    }
                ]
            },
            {
                cols: [
                    { type: "spacer" },
                    {
                        name: "upload",
                        type: "button",
                        value: "업로드"
                    }
                ]
            }
        ]
    });
    layout.getCell("fileuploadContent").attach(fileuploadForm);
};

// 3. 업로드 결과 폼 생성 (result는 fetch 이후 전달)
const createFileuploadResultForm = (result) => {
    let errorRows = [];
    console.log("result", result)
    if (result.errors && result.errors.length > 0) {
        errorRows.push({
            type: "label",
            label: "실패 내역",
            name: "uploadResultFailLabel",
            css: "failLabel"
        });
        result.errors.forEach((err, i) => {
            errorRows.push({
                type: "text",
                name: `failDetail_${i}`,
                label: `라인 ${err.lineCount}`,
                value: err.errors,
                readonly: true,
                css: "failDetailRow"
            });
        });
    }

    fileuploadResultForm = new dhx.Form(null, {
        css: "fileuploadResultForm",
        rows: [
            {
                type: "label",
                label: "<h2>업로드 결과</h2>",
                name: "uploadResultTitle",
                css: "resultTitle"
            },
            {
                type: "text",
                label: "성공",
                name: "uploadResultSuccessCount",
                value: result.successCount + "건",
                readonly: true,
                css: "resultSuccess"
            },
            {
                type: "text",
                label: "실패",
                name: "uploadResultFailCount",
                value: result.errors.length + "건",
                readonly: true,
                css: "resultFail"
            },
            ...errorRows,
            {
                align: "end",
                cols: [
                    { type: "spacer" },
                    {
                        name: "showAll",
                        type: "button",
                        value: "전체조회"
                    }
                ]
            }
        ]
    });
    layout.getCell("fileuploadResultContent").attach(fileuploadResultForm);

    fileuploadResultForm.events.on("click", (name, e) => {
        if (name === "showAll") {
            reloadFileuploadListGrid();
        }
    });
};

// 4. 파일업로드 리스트 그리드 생성
const createFileuploadListGrid = () => {

    if (fileuploadListGrid) {
            layout.getCell("fileuploadResultGridContent").detach();
    }

    fileuploadListGrid = new dhx.Grid(null, {
        columns: [
            { id: "id", header: [{ text: "ID" }] },
            { id: "name", header: [{ text: "이름" }] },
            { id: "level", header: [{ text: "level" }] },
            { id: "desc", header: [{ text: "설명" }] },
            { id: "regDate", header: [{ text: "가입일" }] }
        ],
        autoWidth: true,
        autoHeight: true,
        resizable: true,
        data: []
    });
    layout.getCell("fileuploadResultGridContent").attach(fileuploadListGrid);
};
