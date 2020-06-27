package _2_Subjects

import com.jakewharton.rxrelay2.PublishRelay
import exampleOf
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import printWithLabel

fun main(){
    exampleOf("PublishSubject") {
        val publishSubject = PublishSubject.create<Int>()
        //Отправка значения в PublisherSubject (Только если уже есть подписчик. Иначе ничего не произойдет)
        //Данное значение не отправится т.к еще нет подписчиков
        publishSubject.onNext(1)

        //Подписка на Publisher Subject
        val subscriptionOne = publishSubject.subscribe { int ->
            printWithLabel("Subscriber 1", int)
        }

        val subscriptionTwo = publishSubject
                .subscribe { int ->
                    printWithLabel("Subscriber 2", int)
                }
        publishSubject.onNext(1)

        subscriptionOne.dispose()

        publishSubject.onNext(2)

        publishSubject.onComplete()

        //СОбытие не сработает т.к было вызвано событие onComplete или onError.
        publishSubject.onNext(3)

        subscriptionTwo.dispose()

        //При попытке подписки после завершающего события - получим событе onComplete
        val subscriptionThree = publishSubject.subscribeBy(
                onNext = { printWithLabel("Subscriber 3", it) },
                onComplete = { printWithLabel("Subscriber 3", "Complete") }
        )
        //Не сработает т.к Subject уже выпустил событие onComplete
        publishSubject.onNext(4)
    }

    exampleOf("BehaviorSubject") {
        val subscriptions = CompositeDisposable()
        val behaviorSubject = BehaviorSubject.createDefault("Initial value")

        behaviorSubject.onNext("X")

        //Подписчик
        val subscriptionOne = behaviorSubject.subscribeBy(
                onNext = { printWithLabel("Subscriber 1", it) },
                onError = { printWithLabel("Subscriber 1", it) }
        )

        subscriptions.add(behaviorSubject.subscribeBy(
                onNext = { printWithLabel("Subscriber 2", it) },
                onError = { printWithLabel("Subscriber 2", it) }
        ))

        behaviorSubject.onError(RuntimeException("Error!"))
    }

    exampleOf("BehaviorSubject State") {
        val subscriptions = CompositeDisposable()
        val behaviorSubject = BehaviorSubject.createDefault(0)
        //Ссылка на последнее значение
        println(behaviorSubject.value)

        subscriptions.add(behaviorSubject.subscribeBy {
            printWithLabel("Subscriber 1", it)
        })

        //Ссылка на текущее значение
        behaviorSubject.onNext(1)
        println(behaviorSubject.value)
        subscriptions.dispose()
    }

    exampleOf("ReplaySubject") {
        val subscriptions = CompositeDisposable()
        val replaySubject = ReplaySubject.createWithSize<String>(2)

        replaySubject.onNext("1")
        replaySubject.onNext("2")
        replaySubject.onNext("3")

        subscriptions.add(replaySubject.subscribeBy(
                onNext = { printWithLabel("Subscriber 1", it) },
                onError = { printWithLabel("Subscriber 1", it)}
        ))
        subscriptions.add(replaySubject.subscribeBy(
                onNext = { printWithLabel("Subscriber 2", it) },
                onError = { printWithLabel("Subscriber 2", it)}
        ))

        println()

        replaySubject.onNext("4")

        replaySubject.onError(RuntimeException("Error!"))

        subscriptions.add(replaySubject.subscribeBy(
                onNext = { printWithLabel("Subscriber 3", it) },
                onError = { printWithLabel("Subscriber 3", it)}
        ))
    }

    exampleOf("AsyncSubject") {
        val subscriptions = CompositeDisposable()
        val asyncSubject = AsyncSubject.create<Int>()

        subscriptions.add(asyncSubject.subscribeBy(
                onNext = { printWithLabel("Subscriber 1", it) },
                onComplete = { printWithLabel("Subscriber 1", "Complete") }
        ))

        asyncSubject.onNext(0)
        asyncSubject.onNext(1)
        asyncSubject.onNext(2)
        asyncSubject.onComplete()
        subscriptions.dispose()
    }

    exampleOf("RxRelay") {
        val subscriptions = CompositeDisposable()
        val publishRelay = PublishRelay.create<Int>()
        subscriptions.add(publishRelay.subscribeBy(
                onNext = { printWithLabel("1)", it) }
        ))
        publishRelay.accept(1)
        publishRelay.accept(2)
        publishRelay.accept(3)
    }
}