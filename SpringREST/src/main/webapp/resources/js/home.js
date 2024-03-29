// -- 26 --
/* 
# Vanilla Javascript
· 아무 라이브러리도 사용하지 않는 기본 자바스크립트

# JQuery
· 자바스크립트 문법 길이를 줄인 것
· 한국 개발자들이 이상하게 아주 좋아한다
· 새로운 자기들만의 문법이 있어서 기본 자바스크립트 문법이 잘 안먹힌다
ex) $('#ajax-out').click();
*/

const ajaxOut = document.getElementById('ajax-out');
const ajaxBtn1 = document.getElementById('ajax-btn1');
const ajaxBtn2 = document.getElementById('ajax-btn2');
const ajaxBtn3 = document.getElementById('ajax-btn3');

// -- 36 --
const ajaxPostBtn1 = document.getElementById('ajax-post-btn1');
// -- 38 --
const ajaxPostBtn2 = document.getElementById('ajax-post-btn2');
const ajaxPutBtn1 = document.getElementById('ajax-put-btn1');

// -- 42 --
const ajaxPutBtn2 = document.getElementById('ajax-put-btn2');


ajaxBtn1.addEventListener('click', (e) => {
    // AJAX 요청 인스턴스 생성
    const xhttp = new XMLHttpRequest();

    // 응답이 왔을때의 동작을 설정
    xhttp.addEventListener('readystatechange', (e) => {

        /*
        · readyState 1 : open()이 성공
        · readyState 2 : 요청에 대한 응답이 도착함
        · readyState 3 : 도착한 응답을 처리중
        · readyState 4 : 사용할 준비 완료
        */
        // console.log('readyState:', xhttp.readyState);
        // console.log('httpStatus:', xhttp.status);
        

        // 준비도 다 되었고, httpStatus도 ok인 상황
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            // RestController에서 응답한 데이터(body)가 responseText에 들어있다
            console.log('요청 성공!', xhttp.responseText);
            ajaxOut.innerHTML += xhttp.responseText + '<br>';
        }
    });

    // 요청을 어떤 방식(method), 어디로 보낼지(url) 설정
    xhttp.open('GET', '/springrest/restful/test1');
    
    // 설정이 모두 끝난 요청을 보낸다
    xhttp.send();
});

// -- 28 --
ajaxBtn2.addEventListener('click', (e) => {
    const xhttp = new XMLHttpRequest();

    // 응답이 도착했을 때 해야 할 동작
    xhttp.addEventListener('readystatechange', (e) => {
        if (xhttp.readyState == 4){
            if (xhttp.status == 200) {
                // console.log('hi');
                // ajaxOut.innerHTML = xhttp.responseText + '<br>';

                // -- 30 : 결과 안 나옴 다시 확인해야 함--
                // 서버로부터 응답받은 json 형태의 문자열을 Javascript Object로 변환
                const obj = JSON.parse(xhttp.responseText);

                ajaxOut.innerHTML = `name: ${obj.name}<br>`;
                ajaxOut.innerHTML += `price: ${obj.price}<br>`;
                ajaxOut.innerHTML += `calories: ${obj.calories}`;
            }
        }
    });
    xhttp.open('GET', 'restful/pizza2');
    xhttp.send();
});


// -- 31 --
ajaxBtn3.addEventListener('click', (e) => {
    const xhttp = new XMLHttpRequest();

    xhttp.addEventListener('readystatechange', (e) => {
        if (e.target.readyState == 4 && e.target.status == 200) {
            // console.log(xhttp.responseText);
            // ajaxOut.innerText = xhttp.responseText;

            // -- 33 --
            // XML로 받은 데이터 활용
            // responseXML로 받은 XML Text는 document 타입 객체가 된다
            //console.log(xhttp.responseXML);
            
            // -- 34 --
            const xdocument = xhttp.responseXML;
            
            console.log(xdocument.getElementsByTagName("name")[0].textContent);
            console.log(xdocument.getElementsByTagName("price")[0].textContent);
            console.log(xdocument.getElementsByTagName("calories")[0].textContent);
        }
        });

        // 현재 주소가 http://localhost:8888/springrest이기 때문에 상대 경로 사용 가능
        xhttp.open('GET', 'restful/pizza3');
        xhttp.send();
    });

    // -- 36 --
    ajaxPostBtn1.addEventListener('click', (e) => {
        const xhttp = new XMLHttpRequest();
        xhttp.addEventListener('readystatechange', (e) => {
            if (e.target.readyState == 4 && e.target.status == 200) {
                console.log(e.target.responseText);
            }
        });

        xhttp.open('POST', 'restful/employee');

        // 실어 보낼 데이터가 어떤 타입인지 지정해줘야 한다
        // 데이터를 form 형식으로 보낼 것이기 때문에 form을 보낼때와 똑같이 설정한다
        xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        
        // send(payload) : 데이터를 실어 보낼 수 있다
        xhttp.send('first_name=Lily&last_name=James&salary=7000');
    });

    // -- 40 --
    ajaxPutBtn1.addEventListener('click', (e) => {
        const xhttp = new XMLHttpRequest();

        xhttp.addEventListener('readystatechange', (e) => {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                console.log(xhttp.responseXML);
            }
        });
        
        xhttp.open('PUT', 'restful/employee');
        
        // GET/POST 외의 방식은 'application/x-www-form-urlencoded'를 쓸 수 없다
        xhttp.setRequestHeader('content-type', 'application/json');

        const obj = {
            first_name: 'Harry',
            last_name: 'Potter',
            salary: 4500
        }

        // JSON 형태의 문자열 데이터와 함께 RestController로 요청
        xhttp.send(JSON.stringify(obj));
    });

    // -- 43 --
    ajaxPostBtn2.addEventListener('click', (e) => {
        const xhttp = new XMLHttpRequest();

        xhttp.addEventListener('readystatechange', (e) => {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                ajaxOut.innerHTML = xhttp.responseText;
            }
        });
        
        xhttp.open('POST', 'restful/employee/json');  
        xhttp.setRequestHeader('content-type', 'application/json');
        xhttp.send(JSON.stringify({
            first_name: 'Woo',
            last_name: 'Won',
            salary: 5500
        }));
    });


    // Spring의 ResponseEntity타입으로 원하는 status를 선택해 응답할 수 있다