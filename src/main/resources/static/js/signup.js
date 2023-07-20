
function confirm_username() {

    const username = document.getElementById('username').value;
    alert(username)

    $.ajax({
        type: 'GET',
        url: '/it/users/idCheck',
        contentType: "application/json",
        data: JSON.stringify({username: username}),
    }).done(function (data, textStatus, xhr) {
        // if (data !== '') {
        //     alert('사용할 수 없는 아이디 입니다.');
        //     return;
        // }
        alert(data + textStatus + xhr);
    })
        .fail(function (xhr, textStatus, errorThrown) {
            alert(errorThrown);
        });
}




var send_code = false;

function confirm_email() {
    if (!check_email()) {
        alert('유효하지 않은 이메일 형식 입니다.');
        return false;
    }

    const email = document.getElementById('email').value;

    $.ajax({
        type: 'POST',
        url: '/api/signup/confirm-email/' + encodeURIComponent(email),
        contentType: "application/json",
        // data: JSON.stringify(email)
    }).done(function (data, textStatus, xhr) {
        if (data !== '') {
            alert('사용할 수 없는 이메일 입니다.');
            return;
        }
    })
        .fail(function (xhr, textStatus, errorThrown) {
            alert(errorThrown)
            return;
        });


    // ***** 중복 검사를 통과하면 메일을 보낸다
    $.ajax({
        type: 'POST',
        url: '/api/email/send-email',
        contentType: "application/json",
        data: JSON.stringify(email)
    }).done(function (data, textStatus, xhr) {
        alert('메일에 적힌 인증코드를 입력해주세요.');

        // **** 인증 코드 입력 창을 연다
        var box = document.getElementById("confirm_email_box");
        box.style.display = "block";

        return;
    })
        .fail(function (xhr, textStatus, errorThrown) {
            alert('메일 전송에 실패했습니다.' + errorThrown);
            return;
        });
}

function confirm_authcode() {

    const input = document.getElementById("confirm_email_code").value;

    // ***** 중복 검사를 통과하면 메일을 보낸다
    $.ajax({
        type: 'POST',
        url: '/api/email/confirm-authcode',
        contentType: "application/json",
        data: input
    }).done(function (data, textStatus, xhr) {
        if (data !== '') {
            alert('유효한 코드가 아닙니다');
            return;
        }
        alert('이메일 인증이 완료되었습니다.');
    })
        .fail(function (xhr, textStatus, errorThrown) {
            alert('errorThrown');
        });
}

function toggle_pw() {

    if($("#pw-check").is(":checked")){
        $('#pw1').prop("type", "text");
        $('#pw2').prop("type", "text");
    }else{
        $('#pw1').prop("type", "password");
        $('#pw2').prop("type", "password");
    }
}