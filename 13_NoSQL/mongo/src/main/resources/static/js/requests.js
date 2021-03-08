function getMates(){
	const promise = axios.get("http://localhost:8080/mates");
	return promise.then((response) =>{
		return response.data;
		});
	}

function createStudents(formData){
	const promise = axios.post("http://localhost:8080/students",
	 formData);
	return promise.then((response) =>{
		return response.data;
		});
	}

function replaceMates(){
    const promise = axios.post("http://localhost:8080/replace");
	return promise.then((response) =>{
		return response.data;
		});
}