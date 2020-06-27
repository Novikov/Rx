import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

fun main(args: Array<String>) {

    exampleOf("ignoreElements") {
        val subscriptions = CompositeDisposable()
        val strikes = PublishSubject.create<String>()
        subscriptions.add(
                strikes.ignoreElements() // Returns a Completable, so no onNext in subscribeBy
                        .subscribeBy {
                    println("You’re out!")
                })

        strikes.onNext("X")
        strikes.onNext("X")
        strikes.onNext("X")
        strikes.onComplete()
    }

    exampleOf("elementAt") {
        val subscriptions = CompositeDisposable()
        val strikes = PublishSubject.create<String>()
        subscriptions.add(
                strikes.elementAt(2) // Returns a Maybe, subscribe with onSuccessinstead of onNext
                        .subscribeBy(
                        onSuccess = { println("You’re out!") }
                        ))
        strikes.onNext("A")
        strikes.onNext("B")
        strikes.onNext("C")
    }

    exampleOf("filter") {
        val subscriptions = CompositeDisposable()
        subscriptions.add(
                Observable.fromIterable(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                        .filter { number -> number > 5 }
                        .subscribe {
                            println(it)
                        })
    }

    exampleOf("skip") {
        val subscriptions = CompositeDisposable()
        subscriptions.add(
                Observable.just("A", "B", "C", "D", "E", "F")
                        .skip(3)
                        .subscribe {
                            println(it)
                        })
    }

    exampleOf("skipWhile") {
        val subscriptions = CompositeDisposable()
        subscriptions.add(
                Observable.just(2, 2,3,4)
                        .skipWhile { number ->
                            number % 2 == 0
                        }.subscribe {
                            println(it)
                        })
    }

    exampleOf("skipUntil") {
        val subscriptions = CompositeDisposable()
        val subject = PublishSubject.create<String>()
        val trigger = PublishSubject.create<String>()
        subscriptions.add(subject.skipUntil(trigger)
                .subscribe {
                    println(it)
                })

        subject.onNext("A")
        subject.onNext("B")
        trigger.onNext("X")
        subject.onNext("С")
    }

    exampleOf("take") {
        val subscriptions = CompositeDisposable()
        subscriptions.add(
                Observable.just(1, 2, 3, 4, 5, 6)
                        .take(3)
                        .subscribe {
                            println(it)
                        })
    }

        exampleOf("takeWhile") {
            val subscriptions = CompositeDisposable()
            subscriptions.add(
                    Observable.fromIterable(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1))
                            .takeWhile { number ->
                                number < 5
                            }.subscribe {
                                println(it)
                            })
        }

    exampleOf("takeUntil") {
        val subscriptions = CompositeDisposable()
        val subject = PublishSubject.create<String>()
        val trigger = PublishSubject.create<String>()
        subscriptions.add(
                subject.takeUntil(trigger)
                        .subscribe {
                            println(it)
                        })
        subject.onNext("1")
        subject.onNext("2")
        trigger.onNext("X")
        subject.onNext("3")
    }

    exampleOf("distinctUntilChanged") {
        val subscriptions = CompositeDisposable()
        subscriptions.add(
                Observable.just("Dog", "Cat", "Cat", "Dog")
                        .distinctUntilChanged()
                        .subscribe {
                            println(it)
                        })


    }

    exampleOf("distinctUntilChangedPredicate") {
        val subscriptions = CompositeDisposable()
        subscriptions.add(Observable.just("ABC", "BCD", "CDE", "FGH", "IJK", "JKL", "LMN")
                .distinctUntilChanged { first, second ->
                    second.toList().any { it in first.toList() }
                }
                .subscribe {
                    println(it)
                }
        )
    }

}

