package com.example.administrator.customviewassamble.views.custom;

/**
 * @author Rui Chaoqun
 * @date :2020/3/18 17:03
 * description:
 */
public class c<T extends b<T>> {

    T f9699a;

    T f9700b;

    public void a(T t) {
        T t2 = this.f9699a;
        t.f9698a = t2;
        if (t2 == null) {
            this.f9700b = t;
        }
        this.f9699a = t;
    }

    public void a(c<T> cVar) {
        if (!cVar.b()) {
            T t = cVar.f9700b;
            T t2 = this.f9699a;
            t.f9698a = t2;
            if (t2 == null) {
                this.f9700b = t;
            }
            this.f9699a = cVar.f9699a;
        }
    }

    public T a() {
        T t = this.f9699a;
        if (t == null) {
            return null;
        }
        this.f9699a = t.f9698a;
        if (this.f9699a == null) {
            this.f9700b = null;
        }
        t.f9698a = null;
        return t;
    }

    public boolean b() {
        return this.f9699a == null;
    }

    public void c() {
        this.f9699a = null;
        this.f9700b = null;
    }
}
