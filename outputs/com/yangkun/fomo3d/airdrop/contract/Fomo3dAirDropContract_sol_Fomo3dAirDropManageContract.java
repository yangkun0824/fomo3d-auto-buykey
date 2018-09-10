package com.yangkun.fomo3d.airdrop.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract extends Contract {
    private static final String BINARY = "6080604052600060025534801561001557600080fd5b50336000908152600160208190526040909120805460ff19169091179055610cf6806100426000396000f3006080604052600436106100695763ffffffff60e060020a6000350416633ccfd60b811461006e5780633d8bf7d314610085578063704802751461009d5780638c9d105d146100be578063abfdfa19146100f3578063caee865a1461011a578063de4d82a414610131575b600080fd5b34801561007a57600080fd5b50610083610165565b005b34801561009157600080fd5b506100836004356101ec565b3480156100a957600080fd5b50610083600160a060020a03600435166102c7565b3480156100ca57600080fd5b506100df600160a060020a03600435166103b6565b604080519115158252519081900360200190f35b3480156100ff57600080fd5b506101086103cb565b60408051918252519081900360200190f35b610083600160a060020a03600435166024356103d1565b34801561013d57600080fd5b50610149600435610648565b60408051600160a060020a039092168252519081900360200190f35b3360009081526001602052604090205460ff1615156101bc576040805160e560020a62461bcd02815260206004820152600b6024820152600080516020610cab833981519152604482015290519081900360640190fd5b6040513390303180156108fc02916000818181858888f193505050501580156101e9573d6000803e3d6000fd5b50565b3360009081526001602052604081205460ff161515610243576040805160e560020a62461bcd02815260206004820152600b6024820152600080516020610cab833981519152604482015290519081900360640190fd5b5060005b818110156102bb576000610259610670565b604051809103906000f080158015610275573d6000803e3d6000fd5b508154600180820184556000938452602090932001805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039290921691909117905501610247565b50600280549091019055565b3360009081526001602052604090205460ff16151561031e576040805160e560020a62461bcd02815260206004820152600b6024820152600080516020610cab833981519152604482015290519081900360640190fd5b600160a060020a03811660009081526001602052604090205460ff161561038f576040805160e560020a62461bcd02815260206004820152600b60248201527f61646d696e206578697374000000000000000000000000000000000000000000604482015290519081900360640190fd5b600160a060020a03166000908152600160208190526040909120805460ff19169091179055565b60016020526000908152604090205460ff1681565b60025481565b3360009081526001602052604081205481908190819060ff16151561042e576040805160e560020a62461bcd02815260206004820152600b6024820152600080516020610cab833981519152604482015290519081900360640190fd5b67016345785d8a000034101561048e576040805160e560020a62461bcd02815260206004820152600e60248201527f657468206e6f7420656e6f756768000000000000000000000000000000000000604482015290519081900360640190fd5b85935083600160a060020a03166311a09ae76040518163ffffffff1660e060020a028152600401602060405180830381600087803b1580156104cf57600080fd5b505af11580156104e3573d6000803e3d6000fd5b505050506040513d60208110156104f957600080fd5b50519250600091505b60025482101561006957600080548390811061051a57fe5b9060005260206000200160009054906101000a9004600160a060020a0316905080600160a060020a0316635f72f450846040518263ffffffff1660e060020a02815260040180828152602001915050602060405180830381600087803b15801561058357600080fd5b505af1158015610597573d6000803e3d6000fd5b505050506040513d60208110156105ad57600080fd5b5051156106355780600160a060020a03166352fba25c3488886040518463ffffffff1660e060020a0281526004018083600160a060020a0316600160a060020a03168152602001828152602001925050506000604051808303818588803b15801561061757600080fd5b505af115801561062b573d6000803e3d6000fd5b5050505050610640565b600190910190610502565b505050505050565b600080548290811061065657fe5b600091825260209091200154600160a060020a0316905081565b60405161062a8061068183390190560060806040526001600055600060015534801561001a57600080fd5b506106008061002a6000396000f3006080604052600436106100615763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166352fba25c81146100665780635f72f4501461008c578063d8d38586146100b8578063da7db033146100df575b600080fd5b61008a73ffffffffffffffffffffffffffffffffffffffff600435166024356100f4565b005b34801561009857600080fd5b506100a460043561015a565b604080519115158252519081900360200190f35b3480156100c457600080fd5b506100cd6102bc565b60408051918252519081900360200190f35b3480156100eb57600080fd5b506100cd6102c2565b3482600154836101026102c8565b73ffffffffffffffffffffffffffffffffffffffff9093168352602083019190915260408083019190915251908190036060019082f08015801561014a573d6000803e3d6000fd5b5050600080546001019055505050565b60008054604080517f994e5504000000000000000000000000000000000000000000000000000000008152306004820152602481019290925251829173__Fomo3dAirDropContract.sol:Fomo3dAirD__9163994e550491604480820192602092909190829003018186803b1580156101d257600080fd5b505af41580156101e6573d6000803e3d6000fd5b505050506040513d60208110156101fc57600080fd5b5051604080517faa8e7efe00000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff83166004820152905191925073__Fomo3dAirDropContract.sol:Fomo3dAirD__9163aa8e7efe91602480820192602092909190829003018186803b15801561028257600080fd5b505af4158015610296573d6000803e3d6000fd5b505050506040513d60208110156102ac57600080fd5b5051600181905590921192915050565b60005481565b60015481565b6040516102fc806102d983390190560060806040526040516060806102fc83398101604090815281516020830151919092015160008054600160a060020a031916600160a060020a038516178155808315156101b5576000809054906101000a9004600160a060020a0316600160a060020a031663d87574e06040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156100b057600080fd5b505af11580156100c4573d6000803e3d6000fd5b505050506040513d60208110156100da57600080fd5b505191506100f583346401000000006100096101ca82021704565b90505b808211156101b0576101128564010000000061025e810204565b6000809054906101000a9004600160a060020a0316600160a060020a031663d87574e06040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561017d57600080fd5b505af1158015610191573d6000803e3d6000fd5b505050506040513d60208110156101a757600080fd5b505191506100f8565b6101c7565b6101c78564010000000061025e810204565b32ff5b60008215156101db57506000610258565b508181028183828115156101eb57fe5b041461025857604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601360248201527f536166654d617468206d756c206661696c656400000000000000000000000000604482015290519081900360640190fd5b92915050565b604051600160a060020a038216903490600081818185875af192505050151561028657600080fd5b60008054604080517f3ccfd60b0000000000000000000000000000000000000000000000000000000081529051600160a060020a0390921692633ccfd60b9260048084019382900301818387803b1580156102e057600080fd5b505af11580156102f4573d6000803e3d6000fd5b50505050505600a165627a7a72305820683f770663d268ba66dbbfc055940155191f341459e1e256c60f0e676219cfb0002961646d696e73206f6e6c79000000000000000000000000000000000000000000a165627a7a723058203f1d815324e408448cf75b07c1b7cfda0f5bcb1d33591e520afd4f6e25ad0e810029";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_ADDCHILD = "addChild";

    public static final String FUNC_ADDADMIN = "addAdmin";

    public static final String FUNC_ADMINS_ = "admins_";

    public static final String FUNC_POOLSIZE_ = "poolSize_";

    public static final String FUNC_CHECKANDATTACK = "checkAndAttack";

    public static final String FUNC_POOL_ = "pool_";

    protected Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> withdraw() {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addChild(BigInteger count) {
        final Function function = new Function(
                FUNC_ADDCHILD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(count)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addAdmin(String addr) {
        final Function function = new Function(
                FUNC_ADDADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> admins_(String param0) {
        final Function function = new Function(FUNC_ADMINS_, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> poolSize_() {
        final Function function = new Function(FUNC_POOLSIZE_, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> checkAndAttack(String target, BigInteger multiple, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CHECKANDATTACK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(target), 
                new org.web3j.abi.datatypes.generated.Uint256(multiple)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> pool_(BigInteger param0) {
        final Function function = new Function(FUNC_POOL_, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Fomo3dAirDropContract_sol_Fomo3dAirDropManageContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
