

function uploadFile(){
var formData = new FormData();
var file = document.querySelector('#file');
formData.append("file", file.files[0]);
const promise = createStudents(formData);
promise.then(onFileUpload);
}



function onFileUpload(data){
    const result = document.querySelector('#students-result');
    result.innerHTML = data;
}

//function onMatesReceived(mates){
//	const result = document.querySelector('#mates-result');
//	result.innerHTML = '';
//		mates.forEach( el => {
//			const tr = document.createElement('tr');
//			const td1 = document.createElement('td');
//			const td2 = document.createElement('td');
//			td1.innerHTML = el.registrationDate;
//			td2.innerHTML = el.fullName;
//			if (el.promo){
//			td2.innerHTML += " PROMO"
//			}
//			td2.id = el.id;
//			td2.width = 300;
//			td2.align = "center";
//			result.appendChild(tr)
//			tr.appendChild(td1);
//			tr.appendChild(td2);
//		});
//	}