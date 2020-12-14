const host = location.hostname;

function getTasks(){
	const promise = axios({
	method:'get',
      url: "/tasks",
      responseType:'document'
	});
	return promise.then((response) =>{
		return response.data;
		});
	}

function createTask(title){
	const promise = axios({
	method: 'post',
    url: "/tasks",
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
    url: "/tasks",
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
    url: "/tasks",
    responseType:'document'
    	});
	return promise.then((response) =>{
		return response.data;
		});
	}

function deleteTask(id){
	const promise = axios({
    method:'delete',
    url: "/tasks/" + id,
    responseType:'document'
      	});
	return promise.then((response) =>{
		return response.data;
		});
	}