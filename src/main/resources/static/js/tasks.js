$(document).ready(function () {
    let beehiveId = document.getElementById('beehiveId').value
    getTasks(beehiveId);
});


function getTasks(beehiveId) {
    fetch(`http://localhost:8080/api/${beehiveId}/tasks`, {
        headers: {
            "Accept": "application/json"
        }
    }).then(res => res.json())
        .then(data => {
            $("#tasks").html("");
            showTasks(data);
        });
}

function showTasks(data) {
    for (const task of data) {
        $("#taskTemplate").tmpl(task).appendTo("#tasks");
    }

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

function updateModalDelete(taskId){
$ ("#deleteTaskModalButton").off("click");
    $ ("#deleteTaskModalButton").on("click",function(ev){
        deleteTask(taskId);
    });
}

