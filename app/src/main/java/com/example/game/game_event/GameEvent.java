package com.example.game.game_event;

public class GameEvent {
    //! 通知オブジェクト
    //this.event_request_subject = null;
    //! 最後にイベントコンテナに追加
    //this.on_delete_request_start_events = = null
    GameEvent() {
        //! 通知オブジェクト
        //this.event_request_subject = new EventRequestSubject();
        //! 最後にイベントコンテナに追加
        //this.on_delete_request_start_events = new ArrayList();
    }
    public GameEventType getGameEventType() {
        return GameEventType.Undefined;
    }
//    public Subject getEventRequestSubject() {
//        //return this.event_request_subject;
//    }
    //  public void addOnDeleteRequestFireEvent(event) {
    //  this.on_delete_request_start_events.Add(event);
//}
//    public fireOnDeleteRequestEvent() {
//        for (let i = 0; i < this.on_delete_request_start_events.length; i++) {
//            let event = this.on_delete_request_start_events.At(i);
//            event.GetEventRequestSubject().NotifyAndBuildMessage(this, "Add");
//        } // for
//    }
    public void initialize() {
    }
    public boolean update(float deltaIime) {
        return false;
    }
}