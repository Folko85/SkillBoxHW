function getTasks(){
	const promise = axios({
	method:'get',
      url:"http://localhost:8080/tasks",
      responseType:'document'
	});
	return promise.then((response) =>{
		return response.data;
		});
	}

function createTask(title){
	const promise = axios({
	method: 'post',
    url: "http://localhost:8080/tasks",
    responseType:'document',
	data : {
		title : title
		}
	});
	return promise.then((response) =>{
		return response.data;
		});
	}

function updateTask(title, id){
	const promise = axios({
    method: 'put',
    url: "http://localhost:8080/tasks",
    responseType:'document',
    data : {
        id : id,
        title : title
   		}
   	});
	return promise.then((response) =>{
		return response.data;
		});
	}

function deleteAllTasks(){
	const promise = axios({
    method:'delete',
    url:"http://localhost:8080/tasks",
    responseType:'document'
    	});
	return promise.then((response) =>{
		return response.data;
		});
	}

function deleteTask(id){
	const promise = axios({
    method:'delete',
    url:"http://localhost:8080/tasks/" + id,
    responseType:'document'
      	});
	return promise.then((response) =>{
		return response.data;
		});
	}