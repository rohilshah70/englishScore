import Foundation
import shared

class KotlinError: LocalizedError {
    let throwable: KotlinThrowable
    
    init(_ throwable: KotlinThrowable) {
        self.throwable = throwable
    }
    
    var errorDescription: String? {
        throwable.message
    }
}

