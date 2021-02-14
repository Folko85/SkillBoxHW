init();

function init(){
    const promise = createMates();
	promise.then(onMatesReceived);
}

function replace(){
    const promise = replaceMates();
    promise.then(onMatesReceived);
}

setInterval(replace, 10000)

function onMatesReceived(mates){
	const result = document.querySelector('#mates-result');
	result.innerHTML = '';
		mates.forEach( el => {
			const tr = document.createElement('tr');
			const td1 = document.createElement('td');
			const td2 = document.createElement('td');
			td1.innerHTML = el.registrationDate;
			td2.innerHTML = el.fullName;
			if (el.promo){
			td2.innerHTML += " PROMO"
			}
			td2.id = el.id;
			td2.width = 300;
			td2.align = "center";
			result.appendChild(tr)
			tr.appendChild(td1);
			tr.appendChild(td2);
		});
	}