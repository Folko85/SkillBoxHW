function getTasks(){
	const promise = axios.get("http://localhost:8080/tasks");
	return promise.then((response) =>{
		return response.data;
		});
	}

function createTask(title){
	const promise = axios.post("http://localhost:8080/tasks", {
		title : title
		});
	return promise.then((response) =>{
		return response.data;
		});
	}

function updateTask(title, id){
	const promise = axios.put("http://localhost:8080/tasks", {
		title : title,
		id : id
		});
	return promise.then((response) =>{
		return response.data;
		});
	}

	function deleteAllTasks(){
	const promise = axios.delete("http://localhost:8080/tasks");
	return promise.then((response) =>{
		return response.data;
		});
	}

	function deleteTask(id){
	const promise = axios.delete("http://localhost:8080/tasks/" +id);
	return promise.then((response) =>{
		return response.data;
		});
	}