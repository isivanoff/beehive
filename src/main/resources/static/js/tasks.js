$(document).ready(function () {
    let beehiveId = document.getElementById('beehiveId').value
    getTasks(beehiveId);
});

const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

function getTasks(beehiveId, page = 0) {
    $("#tasks").html("");
    $("#loading").tmpl().appendTo('#tasks');

    fetch(`http://localhost:8080/api/${beehiveId}/tasks?page=${page}`, {
        headers: {
            "Accept": "application/json"
        }
    }).then(res => res.json())
        .then(data => {
            $("#tasks").html("");
            showTasks(data.content);
            updateNav(data, beehiveId);
        });
}

function showTasks(data) {
    for (const task of data) {
        $("#taskTemplate").tmpl(task).appendTo("#tasks");
    }

}

function updateNav(data, beehiveId) {
    $("#firstPage").off("click");
    $("#prevPage").off("click");
    $("#curPage").off("click");
    $("#nextPage").off("click");
    $("#lastPage").off("click");

    $("#firstPage").on("click", function () {
        getTasks(beehiveId);
    });

    if (data.pageable.pageNumber == 0) {
        $("#prevPage").closest("nav").hide();
    } else {
        $("#prevPage").closest("nav").show();
        $("#prevPage").text(data.pageable.pageNumber);
        $("#prevPage").on("click", function () {
            getTasks(beehiveId, data.pageable.pageNumber - 1);
        });
    }

    $("#curPage").text(data.pageable.pageNumber + 1)


    if (data.pageable.pageNumber >= data.totalPages - 1) {
        $("#nextPage").closest("nav").hide();
    } else {
        $("#nextPage").closest("nav").show();
        $("#nextPage").text(data.pageable.pageNumber + 2);
        $("#nextPage").on("click", function () {
            getTasks(beehiveId, data.pageable.pageNumber + 1);
        });
    }

    $("#lastPage").on("click", function () {
        getTasks(beehiveId, data.totalPages - 1);
    });

}

function taskDetails(taskId) {
    $("#fullModalContent").html("");
    $("#loading").tmpl().appendTo('#fullModalContent');
    fetch(`http://localhost:8080/api/tasks/view/${taskId}`, {
        headers: {
            "Accept": "application/json"
        }
    }).then(res => res.json())
        .then(task => {
            $("#fullModalContent").html("");
            $("#taskFullTemplate").tmpl(task).appendTo("#fullModalContent")
            $("#queenAlive").prop("checked", task.queenAlive);
            $("#queenActive").prop("checked", task.queenActive);
            $("#queenMarked").prop("checked", task.queenMarked);

            if (task.queenMarked == false) {
                $("#queenDateOfMark").hide();
            }

            $("#eggs").prop("checked", task.eggs);
            $("#larva").prop("checked", task.larva);
            $("#puppa").prop("checked", task.puppa);
            $("#disease").prop("checked", task.disease);


            $(".modal .progress-bar").addClass(task.temperament + '-bar');
            $("#temper").addClass(task.temperament);
            updateTemperColor();
        });




}

function deleteTask(taskId) {
    var myHeaders = new Headers();
    var beehiveId = document.getElementById('beehiveId').value

    myHeaders.append(csrfHeaderName, csrfHeaderValue  );

    var requestOptions = {
        method: 'DELETE',
        headers: myHeaders,
        redirect: 'follow'
    };



    fetch(`http://localhost:8080/api/tasks/delete/${taskId}`, requestOptions)
        .then(response => response.text())
        .then(result => getTasks(beehiveId))
        .catch(error => console.log('error', error));
}

function updateModalDelete(taskId) {
    $("#deleteTaskModalButton").off("click");
    $("#deleteTaskModalButton").on("click", function (ev) {
        deleteTask(taskId);
    });
}

