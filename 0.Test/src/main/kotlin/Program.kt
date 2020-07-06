import _1_Observables.exampleOf
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

fun main(){
    exampleOf("just"){
        val observable = Observable.just(1,2,3)

        val subscription = observable.subscribeBy(onNext = { println(it)}, onComplete = {println("Complete")})

    }

    exampleOf("create"){

        val disposables = CompositeDisposable()

        val observable = Observable.create<String>{ emitter->
            emitter.onNext("1")
            emitter.onNext("2")

            emitter.onComplete()
        }

        val subscription = observable.subscribeBy(onNext = { println(it)}, onComplete = {println("Complete")})

    }

    exampleOf("Subject"){
        val subscriptions = CompositeDisposable()

        val publishSubject = PublishSubject.create<Int>()

        val subscriptionOne = publishSubject.subscribe{it -> println(it)}

        //в disposable можно добавить только подписки
        subscriptions.add(subscriptionOne)

        publishSubject.onNext(1)

        subscriptions.dispose()

        publishSubject.onNext(1)
    }
}