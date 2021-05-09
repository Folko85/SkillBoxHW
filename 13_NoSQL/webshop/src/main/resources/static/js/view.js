// здесь мы объявляем эндпойнт контроллера контроллер
var itemsApi =Vue.resource('/item/{id}')

//метод получения индекса строки
function getIndex(list, id){
    for(var i=0; i< list.length; i++){
        if(list[i].id == id){
            return i;
        }
    }
    return -1;
}

// это форма добавления товара
Vue.component('item-form', {
    props: ['items', 'itemAttr'],
    data: function(){
        return {
            text: '',
            id: ''
        }
    },
    watch: {
        itemAttr:
            function(newValue, oldValue){
            this.text = newValue.text;
            this.id = newValue.id;
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="Write something" v-model="text" />' +
            '<input type="button" value="Save" v-on:click="save" />' +
        '</div>',
    methods: {
        save: function(){
            var item = {text: this.text};
            if(this.id){
                //если есть id, ТО ИСПОЛЬЗУЕМ МЕТОД update
                itemsApi.update({id: this.id}, item).then( result =>
                            result.json().then(data => {
                                var index = getIndex(this.items, data.id);
                                this.items.splice(index, 1, data);
                                this.text = '';
                            })
                        )
            } else{
            // в противном случае используем метод save
            itemsApi.save({}, item).then( result =>
                            result.json().then(data => {
                                this.items.push(data);
                                this.text = '';
                            })
                        )
            }
        }
    }
});

//каждый товаро из списка можно удалить или изменить
Vue.component('item-raw',{
    props: ['item', 'editItem', 'items'],
    template: '<div>' +
    '<i>({{item.id}})</i> {{item.text}}' +
    '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" v-on:click="edit" />' +
        '<input type="button" value="Delete" v-on:click="del" />' +
    '</span>' +
    '</div>',
    methods: {
            edit: function(){
                        //тут вызываем метод редактирования товара
                        this.editItem(this.item);
                    },
            del: function(){
            // а это метод удаления
                itemsApi.remove({id: this.item.id}).then( result =>{
                    if(result.ok){
                        this.items.splice(this.items.indexOf(this.item), 1);
                    }
                })
            }
            }
});

// это шаблон, определяющий, как будет заполняться наш элемент
Vue.component('item-list', {
    props: ["items"],
    data: function(){
        return {
            item: null,
        }
    },
    template: '<div style="position: relative; width: 300px">' +
    '<item-form :items="items" :itemAttr="item" />' +
    '<item-raw v-for="item in items" :key= "item.id" :item = "item" :editItem="editItem" :items="items" />' +
    '</div>',
    methods: {
    //сам метод просто перемещает редактируемый итем а эту форму
        editItem: function(item){
            this.item = item;
        }
    }
});
// Это наш элемент, найденный по id
var app = new Vue({
  el: '#app',
  template: "<item-list :items = 'items' />",
  // в этой части мы заполняем массив информацией, полученной с контроллера
  data: {items: []},
      created: function (){
          itemsApi.get().then( result =>
              result.json().then ( data =>
                  data.forEach (item => this.items.push(item))
              ))}
});