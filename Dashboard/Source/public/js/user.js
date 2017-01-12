$(document).ready(function () {
    $('#btn-deactivate').click(function(){
        var user_id = $("#p-label-user-id").html();

        jQuery.ajax({
            url: "/users/" + user_id,
            type: "DELETE",
            data: {"user_id" : user_id, "_token": "{{ csrf_token() }}"},
            dataType: "json",
            // headers: {
            //     'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
            // },
            success: function(data){
                alert(data);
                $('#modal-delete').modal('hide');
                location.reload();
            },
            error: function() {
                console.log('error');
            }
        });

//            $.ajax({
//                dataType: 'application/json',
//                url: "/users/" + user_id,
//                type: 'DELETE',
//                contentType: 'application/json; charset=utf-8',
//                success: function (result) {
//                    $('#modal-delete').modal('hide');
//                },
//                failure: function (errMsg) {
//                    alert(errMsg);
//                }
//            });

//            alert('worked');
    });
});