function getIndex(list, id) {
    for(var i = 0; i < list.length; i++){
        if(list[i].id === id){
            return i;
        }
    }
    return -1;
}


var announcementAPI = Vue.resource('announcement{/id}');

Vue.component('announcement-row', {
    props: ['announcement', 'editAnnouncement', 'announcements'],
    template:
        '<div>' +
            '<i>({{announcement.id}})</i>{{announcement.text}}' +
            '<span>' +
                '<input type="button" value="Edit an announcement" @click="edit" />'+
                '<input type="button" value="Delete an announcement" @click="del" />'+
            '</span>'+
        '</div>',
    methods: {
        edit: function () {
            this.editAnnouncement(this.announcement);
        },
        del: function () {
            announcementAPI.remove({id: this.announcement.id}).then(result => {
                if (result.ok){
                    this.announcements.splice(this.announcements.indexOf(this.announcement), 1)
                }
            })
        }
    }
});

Vue.component('announcement-form', {
    props: ['announcements', 'announcementAttr'],
    data: function () {
        return {
            text: '',
            id: ''
        }
    },
    watch: {
        announcementAttr: function(newVal, oldVal){
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
            '<input type="text" placeholder="Title" v-model="text" />' +
            '<div>' +
                '<input type="button" value="Post an announcement" @click="save"/>' +
            '</div>' +
        '</div>',
    methods: {
        save: function () {
            var announcement = {text: this.text};
            if(this.id){
                announcementAPI.update({id: this.id}, announcement).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.announcements,data.id);
                        this.announcements.splice(index, 1, data);
                    })
                )
            } else {
                announcementAPI.save({}, announcement).then(result =>
                    result.json().then(data => {
                        this.announcements.push(data);
                        this.text = '';
                        this.id = ''
                    })
                )
            }
        }
    }
});

Vue.component('announcements-list', {
    props: ['announcements'],
    data: function () {
        return{
            announcement: null
        }
    },
    template:
        '<div>' +
            '<announcement-form :announcements="announcements" :announcementAttr="announcement" />' +
            '<announcement-row v-for="announcement in announcements" :key="announcement.id" :announcement="announcement" ' +
        ':editAnnouncement="editAnnouncement" :announcements="announcements"/>' +
        '</div>',
    methods: {
        editAnnouncement: function (announcement) {
            this.announcement = announcement;
        }
    }
});

var app = new Vue({
    el: '#app',
    template:
        '<div>'+
            '<div v-if="!profile">You are not authorized.<a href="/login"> Please login </a></div>'+
            '<div v-else>'+
                '<div>{{profile.username}} <a href="/login?logout">Logout</a></div>'+
                '<announcements-list :announcements="announcements" />' +
            '</div>'+
        '</div>',
    data: {
        announcements: frontendDate.announcements,
        profile: frontendDate.profile
    },
    created: function () {
        // announcementAPI.get().then(result =>
        //     result.json().then(data =>
        //         data.forEach(announcement => this.announcements.push(announcement))
        //     )
        // )
    }
});