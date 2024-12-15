$(function () {
    const $registerForm = $('#registerForm');

    if ($registerForm.length) {
        $registerForm.validate({ //validate() 함수를 호출하여 검증 처리
            rules: { //검증할 규칙 정의
                name: {
                    required: true,
                    minlength: 2
                },
                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 4,
                    maxlength: 15
                },
                confirmPassword: {
                    required: true,
                    equalTo: '#password'
                }
            },
            messages : { //검증 실패시 메시지
                name: {
                    required: '이름을 입력해 주세요',
                    minlength: '적어도 2개의 문자이상 작성해주세요'
                },
                email: {
                    required: '이메일을 입력해 주세요',
                    email: '이메일 형식에 맞게 입력해 주세요'
                },
                password: {
                    required: '패스워드 입력해 주세요',
                    minlength: '패스워드는 최소 4자 이상',
                    maxlength: '패스워드는 최대 15자 까지'
                },
                confirmPassword: {
                    required: '패스워드 확인 입력해 주세요',
                    equalTo: '패스워드가 같지 않습니다!'
                }
            },
            errorElement: 'span',
            errorPlacement: function (error,element){
                error.addClass('help-block');
                error.insertAfter(element);
            }
        })
    }
})