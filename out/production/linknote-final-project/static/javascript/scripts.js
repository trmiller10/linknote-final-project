/*function setAction(action){
    document.getElementById("noteListActions").setAttribute("action", action);
    if(action = "/delete-notes") {
        msg = "The checked notes will be deleted from your list and cannot be recovered at this time!  Do you wish to continue?";
        var deleteThis = confirm(msg);
        if(deleteThis == false){
            document.getElementById("noteListActions").setAttribute("action", "redirect:/home");
        }
        if(deleteThis == true){
            document.getElementById("noteListActions").setAttribute("action", "/delete-notes");
        }
    }else if(action = "/merge-notes") {
        document.getElementById("noteListActions").setAttribute("action", "/merge-notes");
    }
}*/

function mergeNotes(){
    msg = "Merged note added to your list!";
    alert(msg);
    document.getElementById("noteListActions").setAttribute("action", "/merge-notes");
    console.log("Merging notes.");
}
function deleteNotes(){
    msg = "The checked notes will be deleted from your list and cannot be recovered at this time!  Do you wish to continue?";
    var deleteThis = confirm(msg);
    if(deleteThis == false){
        document.getElementById("noteListActions").setAttribute("action", "redirect:/home");
    }
    if(deleteThis == true){
        document.getElementById("noteListActions").setAttribute("action", "/delete-notes");
    }
    console.log("Deleting notes.");
}

