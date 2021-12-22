package com.example.game.collision;

import com.example.game.collision.CollisionInfo;
import com.example.game.collision.Collisionable;

import java.util.ArrayList;
import java.util.List;

public class CollisionLayer {
    class FuncExecute {
        Collisionable executor;
        Collisionable target;
        CollisionInfo info;

        public FuncExecute(
                Collisionable executor,
                Collisionable target,
                CollisionInfo info
        ) {
            this.executor = executor;
            this.target = target;
            this.info = info;
        }

    }

    // 総当たり
    private List<Collisionable> collisionable = new ArrayList<Collisionable>();
    private List<FuncExecute> funcExecuteEnter = new ArrayList<FuncExecute>();
    private List<FuncExecute> funcExecuteStay = new ArrayList<FuncExecute>();
    private List<FuncExecute> funcExecuteExit = new ArrayList<FuncExecute>();

    public CollisionLayer() {
    }

    public void add(Collisionable collisionable) {
        assert (collisionable != null);
        this.collisionable.add(collisionable);
    }

    ;

    public void remove(Collisionable collisionable) {
        assert (collisionable != null);
        this.collisionable.remove(collisionable);
    }

    ;

    //! 静的な衝突
    public void excute() {
        for (Collisionable object : collisionable) {
            for (Collisionable target : collisionable) {
                if (object == target) {
                    continue;
                } // if
                CollisionInfo info = new CollisionInfo();
                if (!object.isCollision(target, info)) {
                    // 終了
                    if (object.existCollisioned(target)) {
                        //object.executeExitFunction(target, info);
                        this.funcExecuteExit.add(
                                new FuncExecute(object, target, info)
                        );
                        object.removeCollisioned(target);
                    } // if
                    continue;
                } // if
                // 開始
                if (!object.existCollisioned(target)) {
                    //object.executeEnterFunction(target, info);
                    this.funcExecuteEnter.add(
                            new FuncExecute(object, target, info)
                    );
                    object.addCollisioned(target);
                } // if
                //! 衝突中
                else {
                    //object.executeStayFunction(target, info);
                    this.funcExecuteStay.add(
                            new FuncExecute(object, target, info)
                    );
                } // else
            } // for
        } // for


        this.executeEnterFunc(this.funcExecuteEnter);
        this.executeStayFunc(this.funcExecuteStay);
        this.executeExitFunc(this.funcExecuteExit);
    }

    void executeEnterFunc(List<FuncExecute> func) {
        for (FuncExecute execute : func) {
            execute.executor.executeEnterFunction(execute.target, execute.info);
        } // for
        func.clear();
    }

    void executeStayFunc(List<FuncExecute> func) {
        for (FuncExecute execute : func) {
            execute.executor.executeStayFunction(execute.target, execute.info);
        } // for
        func.clear();
    }

    void executeExitFunc(List<FuncExecute> func) {
        for (FuncExecute execute : func) {
            execute.executor.executeExitFunction(execute.target, execute.info);
        } // for
        func.clear();
    }

}