const URL_API = "/api/v1";

let isUpdate = false;
let idUpdate = null;

// truy cap vao cac thanh phan
const todoListEl = document.querySelector(".todo-list");
const todoOptionItemsEl = document.querySelectorAll(".todo-option-item input");

const todoInput = document.getElementById("todo-input");
const btnAdd = document.getElementById("btn-add");

// Danh sach URL_API
const getTodoAPI = (status) => {
    switch (status) {
        case "all": {
            return axios.get(`${URL_API}/todos`);
        }
        case "unactive": {
            return axios.get(`${URL_API}/todos?status=false`);
        }
        case "active": {
            return axios.get(`${URL_API}/todos?status=true`);
        }
        default: {
            return axios.get(`${URL_API}/todos`);
        }
    }
};

const deleteTodoAPI = (id) => {
    return axios.delete(`${URL_API}/todos/${id}`);
};

const updateTodoAPI = (todo) => {
    return axios.put(`${URL_API}/todos/${todo.id}`, {
        title: todo.title,
        status: todo.status,
    });
};

const createTodoAPI = title => {
    return axios.post(`${URL_API}/todos/`, {
        title: title,
    });
}


// cac ham xu ly
const getTodos = async (status = "") => {
    try {
        let res = await getTodoAPI(status);

        todos = res.data;

        renderTodo(todos);
    } catch (error) {
        console.log(error);
    }
};

// hien thi mang
const renderTodo = (arr) => {
    todoListEl.innerHTML = "";
    if (arr.length === 0) {
        todoListEl.innerHTML = "Khong co cong viec trong danh sach";
        return;
    }

    let html = "";
    for (var i = 0; i < arr.length; i++) {
        const t = arr[i];
        html += `
        <div class="todo-item ${t.status ? "active-todo" : ""}">
                    <div class="todo-item-title">
                        <input type="checkbox"  ${t.status ? "checked" : ""}
                        onclick="toggleStatus(${t.id})"/>
                        <p>${t.title}</p>
                    </div>
                    <div class="option">
                        <button class="btn btn-update" onclick="updateTitle(${
            t.id
        })"
                        })">
                            <img src="./img/pencil.svg" alt="icon" />
                        </button>
                        <button class="btn btn-delete" onclick="deleteTodo(${
            t.id
        })">
                            <img src="./img/remove.svg" alt="icon" />
                        </button>
                    </div>
                </div>
        `;
    }
    todoListEl.innerHTML = html;
};

async function updateTodo(todoUpdate) {
    try {
        // Gọi API cập nhật
        let res = await updateTodoAPI(todoUpdate);

        // Cập nhật lại trong mảng todos
        todos.forEach((todo, index) => {
            if (todo.id == todoUpdate.id) {
                todos[index] = res.data;
            }
        });

        // Thay đổi giao diện về ban đầu
        btnAdd.innerText = "Thêm";

        // Reset lại biến sau khi đã cập nhật thành công
        isUpdate = false;
        idUpdate = null;

        renderTodo(todos);
    } catch (error) {
        console.log(error);
    }
}

const deleteTodo = async (id) => {
    try {
        let isConfirm = confirm("Are you sure you want to delete?");
        if (isConfirm) {
            // xoa tren service

            await deleteTodoAPI(id);

            //xoa trong mang todos
            todos.forEach((todo, index) => {
                if (todo.id == id) {
                    todos.splice(index, 1);
                }
            });

            // render lai giao dien
            renderTodo(todos);
        }
    } catch (error) {
        console.log(error);
    }
};

// Cập nhật tiêu đề todo
function updateTitle(id) {
    let title;

    // Tìm kiếm công việc muốn cập nhật và lưu lại giá trị title
    todos.forEach((todo) => {
        if (todo.id == id) {
            title = todo.title;
        }
    });

    // Đổi tên "THÊM" -> "CẬP NHẬT"
    btnAdd.innerText = "CẬP NHẬT";

    // Hiển thị tiêu đề cần cập nhật lên ô input
    todoInput.value = title;
    todoInput.focus();

    // Lưu lại id của công việc cần cập nhật và cho phép cập nhật
    idUpdate = id;
    isUpdate = true;
}

btnAdd.addEventListener("click", function () {
    // Lấy tiêu đề trong ô input
    let todoTitle = todoInput.value;

    // Kiểm tra xem tiêu đề có trống hay không
    if (todoTitle == "") {
        alert("Nội dung không được để trống!");
        return;
    }

    // Nếu isUpdate == true thì cho phép cập nhật
    // Ngược lại isUpdate == false thì cho phép thêm
    if (isUpdate) {
        // Tìm công viêc có id = idUpdate
        let todo = todos.find((todo) => todo.id == idUpdate);

        // Sửa lại tiêu đề của công việc đó = nội dung trong ô input
        todo.title = todoTitle;

        // Thực hiện cập nhật
        updateTodo(todo);
    } else {
        // Thực hiện thêm công việc
        createTodo(todoTitle);
    }

    todoInput.value = "";
});

const toggleStatus = async (id) => {
    try {
        // lay ra cong viec can update trong mang todos
        let todo = todos.find((todo) => todo.id == id);

        // thay doi trang thai cong viec
        todo.status = !todo.status;

        let res = await updateTodoAPI(todo);

        renderTodo(todos);
    } catch (error) {
        console.log(error);
    }
};

// Lọc công việc theo trạng thái
// Lắng nghe sự kiện trên các ô input
todoOptionItemsEl.forEach((input) => {
    input.addEventListener("change", function () {
        // Nếu ô input vào đang được chọn --> lấy ra value
        let status = input.value;

        // Gọi API để lấy công việc theo trạng thái --> hiển thị
        getTodos(status);
    });
});

// Hàm xử lý việc thêm
async function createTodo(title) {
    try {
        // Gọi API tạo todo
        const res = await createTodoAPI(title);

        // Khi có kết quả trả về từ server thì thêm vào trong mảng todos
        todos.push(res.data)

        // Render ra ngoài giao diện
        renderTodo(todos);
    } catch (error) {
        console.log(error);
    }
}
