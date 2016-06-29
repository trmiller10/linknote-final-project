function setAction(action){
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
}


