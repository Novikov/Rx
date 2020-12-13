import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception
import java.lang.IllegalArgumentException

fun main(){
    val compositeDisposable = CompositeDisposable()

    try {
        compositeDisposable.add(rxFunction()
            .retry(2)
            .onErrorReturn { throw IllegalArgumentException("Som") }
            .subscribe({ println(it)},{
                it.printStackTrace()
            }))
    }
    catch (ex:Exception){
        ex.printStackTrace()
    }
}

object Switch{
    var switch = true
}

fun rxFunction(): Observable<String> {
    return Observable.create<String> { emitter ->
        if (Switch.switch) {
            emitter.onError(throw Exception("Hello, Friend!"))
        }
        emitter.onNext("1")
        emitter.onNext("2")
        emitter.onNext("3")
        emitter.onComplete()
    }.doOnNext { println(it) }
        .doOnError {
            println("now Switch " + Switch.switch)
//                Switch.switch = false }
        }
}
